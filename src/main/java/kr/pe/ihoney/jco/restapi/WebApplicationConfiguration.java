package kr.pe.ihoney.jco.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pe.ihoney.jco.restapi.common.mapper.HibernateAwareObjectMapper;
import kr.pe.ihoney.jco.restapi.web.support.converter.LongIdTypeEntityConverter;
import kr.pe.ihoney.jco.restapi.web.support.interceptor.PageStatusAutoPersistenceInterceptor;
import kr.pe.ihoney.jco.restapi.web.support.method.PageStatusHandlerMethodArgumentResolver;
import kr.pe.ihoney.jco.restapi.web.support.method.PageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Locale;

/**
 * API 설정 context
 * 
 * @Configuration: mvc:annotation-driven
 * @EnableAsync: task:annotation-driven
 * @ComponentScan: context:component-scan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "kr.pe.ihoney.jco.restapi.web")
public class WebApplicationConfiguration extends WebMvcConfigurerAdapter {

    @Inject
    private EntityManagerFactory entityManagerFactory;
    private String PARAM_NAME = "lang";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);

        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter()); // JSON
        converters.add(new Jaxb2RootElementHttpMessageConverter()); // XML
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(longIdTyoeConverter());
    }

    @Bean
    public LongIdTypeEntityConverter longIdTyoeConverter() {
        return new LongIdTypeEntityConverter();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageStatusHandlerMethodArgumentResolver());
        argumentResolvers.add(pageableHandlerMethodArgumentResolver());
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }

    @Bean
    public PageStatusHandlerMethodArgumentResolver pageStatusHandlerMethodArgumentResolver() {
        return new PageStatusHandlerMethodArgumentResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor()).addPathPatterns(
                new String[] { "/communities/**", "/users/**", "/posts/**" });
        registry.addWebRequestInterceptor(pageStatusAutoPersistenceInterceptor());
        registry.addInterceptor(webContentInterceptor());
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public PageStatusAutoPersistenceInterceptor pageStatusAutoPersistenceInterceptor() {
        return new PageStatusAutoPersistenceInterceptor();
    }

    @Bean
    public HandlerInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(PARAM_NAME);
        return localeChangeInterceptor;
    }

    /**
     * 참조:
     * http://zeroturnaround.com/rebellabs/your-next-java-web-app-less-xml-no
     * -long-restarts-fewer-hassles-part-2/
     * 
     * @return
     */
    @Bean
    public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        return openEntityManagerInViewInterceptor;
    }

    @Bean
    public WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setUseExpiresHeader(true);
        webContentInterceptor.setUseCacheControlNoStore(true);
        webContentInterceptor.setUseCacheControlHeader(true);
        return webContentInterceptor;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new HibernateAwareObjectMapper();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

}
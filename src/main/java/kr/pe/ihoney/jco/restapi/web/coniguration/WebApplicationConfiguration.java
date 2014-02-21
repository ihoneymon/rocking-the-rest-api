package kr.pe.ihoney.jco.restapi.web.coniguration;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import kr.pe.ihoney.jco.restapi.common.mapper.HibernateAwareObjectMapper;
import kr.pe.ihoney.jco.restapi.web.support.converter.LongIdTypeEntityConverter;
import kr.pe.ihoney.jco.restapi.web.support.interceptor.PageStatusAutoPersistenceInterceptor;
import kr.pe.ihoney.jco.restapi.web.support.method.PageStatusHandlerMethodArgumentResolver;
import kr.pe.ihoney.jco.restapi.web.support.method.PageableHandlerMethodArgumentResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

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
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    ContentNegotiatingViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setContentNegotiationManager(manager);
        contentNegotiatingViewResolver.setViewResolvers(getViewResolvers());
        contentNegotiatingViewResolver.setDefaultViews(getDefaultViews());

        return contentNegotiatingViewResolver;
    }

    private List<ViewResolver> getViewResolvers() {
        List<ViewResolver> viewResolvers = Lists.newArrayList();
        viewResolvers.add(beanNameViewResolver());
        return viewResolvers;
    }

    private BeanNameViewResolver beanNameViewResolver() {
        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        return beanNameViewResolver;
    }

    private List<View> getDefaultViews() {
        List<View> views = Lists.newArrayList();
        views.add(getMappingJackson2JsonView());
        return views;
    }

    private View getMappingJackson2JsonView() {
        return new MappingJackson2JsonView();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(longIdTyoeConverter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter()); // JSON
        converters.add(new Jaxb2RootElementHttpMessageConverter()); // XML
    }

    @Bean
    LongIdTypeEntityConverter longIdTyoeConverter() {
        return new LongIdTypeEntityConverter();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageStatusHandlerMethodArgumentResolver());
        argumentResolvers.add(pageableHandlerMethodArgumentResolver());
    }

    @Bean
    PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }

    @Bean
    PageStatusHandlerMethodArgumentResolver pageStatusHandlerMethodArgumentResolver() {
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
    PageStatusAutoPersistenceInterceptor pageStatusAutoPersistenceInterceptor() {
        return new PageStatusAutoPersistenceInterceptor();
    }

    @Bean
    HandlerInterceptor localeChangeInterceptor() {
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
    OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        return openEntityManagerInViewInterceptor;
    }

    @Bean
    WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setUseExpiresHeader(true);
        webContentInterceptor.setUseCacheControlNoStore(true);
        webContentInterceptor.setUseCacheControlHeader(true);
        return webContentInterceptor;
    }

    @Bean
    ObjectMapper objectMapper() {
        return new HibernateAwareObjectMapper();
    }
    
    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }
}
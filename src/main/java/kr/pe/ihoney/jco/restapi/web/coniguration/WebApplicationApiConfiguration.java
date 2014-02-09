package kr.pe.ihoney.jco.restapi.web.coniguration;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import kr.pe.ihoney.jco.restapi.common.mapper.HibernateAwareObjectMapper;
import kr.pe.ihoney.jco.restapi.web.support.converter.LongIdTypeEntityConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * API 설정 context
 * 
 * @Configuration: mvc:annotation-driven
 * @EnableAsync: task:annotation-driven
 * @ComponentScan: context:component-scan
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "kr.pe.ihoney.jco.restapi.web.api")
public class WebApplicationApiConfiguration extends WebMvcConfigurerAdapter {

    @Inject
    private EntityManagerFactory entityManagerFactory;
    
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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor()).addPathPatterns(new String[] {"/communities/**", "/users/**", "/posts/**"});
        registry.addInterceptor(webContentInterceptor());
    }


    /**
     * 참조: http://zeroturnaround.com/rebellabs/your-next-java-web-app-less-xml-no-long-restarts-fewer-hassles-part-2/
     * @return
     */
    private OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        return openEntityManagerInViewInterceptor;
    }

    private WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setUseExpiresHeader(true);
        webContentInterceptor.setUseCacheControlNoStore(true);
        webContentInterceptor.setUseCacheControlHeader(true);
        return webContentInterceptor;
    }
    
    /**
     * <a href="http://stackoverflow.com/questions/11273443/how-to-configure-spring-conversionservice-with-java-config">ConversionService</a>
     * @return
     */
    @Bean
    public ConversionService conversionService() {
        FormattingConversionServiceFactoryBean bean = new FormattingConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    private Set<?> getConverters() {
        Set<GenericConverter> converters = Sets.newHashSet();
        converters.add(new LongIdTypeEntityConverter());
        return converters;
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
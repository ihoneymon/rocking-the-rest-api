package kr.pe.ihoney.jco.restapi.web.coniguration;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.collect.Lists;

/**
 * View 설정 context.xml
 * 
 * @Configuration: mvc:annotation-driven
 * @EnableAsync: task:annotation-driven
 * @ComponentScan: context:component-scan
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "kr.pe.ihoney.jco.restapi.web.view")
public class WebApplicationViewConfiguration extends WebMvcConfigurerAdapter {

    private static final String PARAM_NAME = "lang";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/").addResourceLocations("/resources/**");
    }

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
        viewResolvers.add(getInternalResourceViewResolver());
        return viewResolvers;
    }

    private InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    private List<View> getDefaultViews() {
        List<View> views = Lists.newArrayList();
        views.add(getMappingJackson2JsonView());
        return views;
    }

    private View getMappingJackson2JsonView() {
        return new MappingJackson2JsonView();
    }
    
    @Bean
    SessionLocaleResolver localReslover() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLocaleChangeInterceptor());
    }

    private HandlerInterceptor getLocaleChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(PARAM_NAME);
        return localeChangeInterceptor;
    }
}
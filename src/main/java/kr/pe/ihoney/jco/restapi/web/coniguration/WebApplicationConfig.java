package kr.pe.ihoney.jco.restapi.web.coniguration;

import kr.pe.ihoney.jco.restapi.configuration.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 1:19
 * @Configuration: mvc:annotation-driven
 * @EnableAsync: task:annotation-driven
 * @ComponentScan: context:component-scan
 */

@Slf4j
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "kr.pe.ihoney.jco.restapi.web", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

    @Bean
    InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/").addResourceLocations("/resources/**");
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }
}

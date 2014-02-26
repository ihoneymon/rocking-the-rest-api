package kr.pe.ihoney.jco.restapi.configuration;

import kr.pe.ihoney.jco.restapi.common.spring.ResourceBundleMessageSource;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Application Properties Configuration
 */
@Configuration
public abstract class PropertiesConfiguration {

    private static final String VIEW_MESSAGE_PROPERTIES = "classpath:/META-INF/properties/view.message";
    private static final String SYSTEM_MESSAGE_PROPERTIES = "classpath:/META-INF/properties/system.message";
    private static final String DEFAULT_ENCODING = "UTF-8";

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(getBaseNames());
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    private String[] getBaseNames() {
        return new String[] { VIEW_MESSAGE_PROPERTIES, SYSTEM_MESSAGE_PROPERTIES };
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }
    
    abstract PropertiesFactoryBean appProperties();
    abstract PropertyPlaceholderConfigurer propertyPlaceholderConfigurer();
}

package kr.pe.ihoney.jco.restapi.configuration;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 2:09
 */
@Configuration
public class PropertiesConfig {

    /**
     * PropertyPlaceholder는 다른 빈들보다 먼저 생성되어야 하기에 static 선언
     * @return PropertyPlaceholderConfigurer
     */
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new Resource[] {new ClassPathResource("/META-INF/application-config.xml")});
        return ppc;
    }

    @Bean
    public PropertiesFactoryBean appProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource[] resources = {new ClassPathResource("/META-INF/application-config.xml")};
        propertiesFactoryBean.setLocations(resources);
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        return propertiesFactoryBean;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/META-INF/properties/view.message");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }
}

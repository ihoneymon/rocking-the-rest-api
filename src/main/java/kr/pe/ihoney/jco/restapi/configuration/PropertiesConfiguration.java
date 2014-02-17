package kr.pe.ihoney.jco.restapi.configuration;

import kr.pe.ihoney.jco.restapi.common.spring.ResourceBundleMessageSource;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Application Properties Configuration
 */
@Configuration
public class PropertiesConfiguration {

    private static final String META_INF_APPLICATION_CONFIG_XML = "/META-INF/application-properties.xml";
    private static final String VIEW_MESSAGE_PROPERTIES = "classpath:/META-INF/properties/view.message";
    private static final String SYSTEM_MESSAGE_PROPERTIES = "classpath:/META-INF/properties/system.message";
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * PropertyPlaceholder는 다른 빈들보다 먼저 생성되어야 하기에 static 선언
     * <p>
     *  <pre>사용 예: ${}, @Value("${key}")</pre>
     * </p>
     * @return PropertyPlaceholderConfigurer
     */
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new Resource[] { new ClassPathResource(META_INF_APPLICATION_CONFIG_XML) });
        return ppc;
    }

    /**
     * <p>
     *  <pre>사용 예: "#{appProperties['key']}"</pre>
     * </p>
     * @return
     */
    @Bean
    public PropertiesFactoryBean appProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource[] resources = { new ClassPathResource(META_INF_APPLICATION_CONFIG_XML) };
        propertiesFactoryBean.setLocations(resources);
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        return propertiesFactoryBean;
    }

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
}

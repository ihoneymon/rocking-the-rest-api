package kr.pe.ihoney.jco.restapi.configuration;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Application Properties Configuration
 */
@Profile("test")
public class TestPropertiesConfiguration extends PropertiesConfiguration{

    private static final String APPLICATION_TEST_CONFIG_XML = "/META-INF/application-test-properties.xml";

    /**
     * <p>
     * 
     * <pre>
     * 사용 예: ${}, @Value("${key}")
     * </pre>
     * 
     * </p>
     * 
     * @return PropertyPlaceholderConfigurer
     */
    @Bean
    @Override
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new Resource[] { new ClassPathResource(
                APPLICATION_TEST_CONFIG_XML) });
        return ppc;
    }

    /**
     * <p>
     * 
     * <pre>
     * 사용 예: "#{appProperties['key']}"
     * </pre>
     * 
     * </p>
     * 
     * @return
     */
    @Bean
    @Override
    public PropertiesFactoryBean appProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource[] resources = { new ClassPathResource(
                APPLICATION_TEST_CONFIG_XML) };
        propertiesFactoryBean.setLocations(resources);
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        return propertiesFactoryBean;
    }

}

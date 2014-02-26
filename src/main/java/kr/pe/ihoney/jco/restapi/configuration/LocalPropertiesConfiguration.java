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
@Profile("local")
public class LocalPropertiesConfiguration extends PropertiesConfiguration {

    private static final String APPLICATION_LOCAL_CONFIG_XML = "/META-INF/application-local-properties.xml";

    /**
     * PropertyPlaceholder는 다른 빈들보다 먼저 생성되어야 하기에 static 선언
     * <p>
     *  <pre>사용 예: ${}, @Value("${key}")</pre>
     * </p>
     * @return PropertyPlaceholderConfigurer
     */
    @Bean
    @Override
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new Resource[] { new ClassPathResource(APPLICATION_LOCAL_CONFIG_XML) });
        return ppc;
    }

    /**
     * <p>
     *  <pre>사용 예: "#{appProperties['key']}"</pre>
     * </p>
     * @return
     */
    @Bean
    @Override
    public PropertiesFactoryBean appProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource[] resources = { new ClassPathResource(APPLICATION_LOCAL_CONFIG_XML) };
        propertiesFactoryBean.setLocations(resources);
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        return propertiesFactoryBean;
    }
}

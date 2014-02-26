package kr.pe.ihoney.jco.restapi.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * application-context.xml 대체
 * 
 * @author ihoneymon
 * 
 */
@Configuration
@Import(value = { LocalPropertiesConfiguration.class,
        TestPropertiesConfiguration.class, DatabaseConfiguration.class })
@ComponentScan(basePackages = "kr.pe.ihoney.jco.restapi", excludeFilters = {
        @ComponentScan.Filter(Controller.class),
        @ComponentScan.Filter(Configuration.class) })
public class ApplicationConfiguration {
    // Rocking
}

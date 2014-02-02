package kr.pe.ihoney.jco.restapi.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 1:18
 */
@Configuration
@Import(value={DatabaseConfig.class, PropertiesConfig.class})
@ComponentScan(basePackages = "kr.pe.ihoney.jco.restapi", excludeFilters = {@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(Configuration.class)})
public class ApplicationConfig {

}

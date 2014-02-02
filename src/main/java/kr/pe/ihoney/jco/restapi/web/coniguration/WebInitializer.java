package kr.pe.ihoney.jco.restapi.web.coniguration;

import kr.pe.ihoney.jco.restapi.configuration.ApplicationConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * web.xml 대체하기
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 1:10
 */
public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ApplicationConfig.class);
        servletContext.addListener(new ContextLoaderListener(applicationContext));

        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebApplicationConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("dispatchOptionsRequest", "true");

        /**
         * HiddenHttpMethodFilter
         */
        servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class).addMappingForUrlPatterns(null, false, "/*");

        /**
         * CharacterEncodingFilter
         */
        FilterRegistration encodingFilterRegistration = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilterRegistration.setInitParameter("encoding","UTF-8");
        encodingFilterRegistration.setInitParameter("forceEncoding","true");
        encodingFilterRegistration.addMappingForUrlPatterns(null, false, "/*");
    }
}

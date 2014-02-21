package kr.pe.ihoney.jco.restapi.web.configuration;

import kr.pe.ihoney.jco.restapi.configuration.ApplicationConfiguration;
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
        applicationContext.register(ApplicationConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(applicationContext));

        /**
         * apiServlet 생성
         */
        AnnotationConfigWebApplicationContext webApplicationApiContext = new AnnotationConfigWebApplicationContext();
        webApplicationApiContext.register(WebApplicationConfiguration.class);
        ServletRegistration.Dynamic apiServlet = servletContext.addServlet("apiServlet", new DispatcherServlet(webApplicationApiContext));
        apiServlet.addMapping("/*");
        apiServlet.setLoadOnStartup(1);
        apiServlet.setInitParameter("dispatchOptionsRequest", "true");

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

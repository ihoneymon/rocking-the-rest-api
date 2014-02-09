package kr.pe.ihoney.jco.restapi.web.coniguration;

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
         * appApiServlet 생성
         */
        AnnotationConfigWebApplicationContext webApplicationApiContext = new AnnotationConfigWebApplicationContext();
        webApplicationApiContext.register(WebApplicationApiConfiguration.class);
        ServletRegistration.Dynamic appApiServlet = servletContext.addServlet("appApiServlet", new DispatcherServlet(webApplicationApiContext));
        appApiServlet.addMapping("/api/*");
        appApiServlet.setLoadOnStartup(1);
        appApiServlet.setInitParameter("dispatchOptionsRequest", "true");
        
        /**
         * appViewServlet 생성
         */
        AnnotationConfigWebApplicationContext webApplicationViewContext = new AnnotationConfigWebApplicationContext();
        webApplicationViewContext.register(WebApplicationViewConfiguration.class);
        ServletRegistration.Dynamic appViewServlet = servletContext.addServlet("appViewServlet", new DispatcherServlet(webApplicationViewContext));
        appViewServlet.addMapping("/view/*");
        appViewServlet.setLoadOnStartup(2);
        appViewServlet.setInitParameter("dispatchOptionsRequest", "true");

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

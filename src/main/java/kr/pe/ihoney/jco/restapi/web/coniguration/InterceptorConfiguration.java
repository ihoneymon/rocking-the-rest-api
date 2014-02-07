package kr.pe.ihoney.jco.restapi.web.coniguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    private static final String PARAM_NAME = "lang";

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(getLocaleChangeInterceptor());
        registry.addInterceptor(getWebContentInterceptor());
    }

    private HandlerInterceptor getLocaleChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(PARAM_NAME);
        return localeChangeInterceptor;
    }

    private HandlerInterceptor getWebContentInterceptor() {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setUseExpiresHeader(true);
        webContentInterceptor.setUseCacheControlHeader(true);
        webContentInterceptor.setUseCacheControlNoStore(true);
        return webContentInterceptor;
    }
}

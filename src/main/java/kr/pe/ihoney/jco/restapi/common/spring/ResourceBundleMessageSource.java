package kr.pe.ihoney.jco.restapi.common.spring;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ResourceBundleMessageSource extends ReloadableResourceBundleMessageSource implements BundleMessageSource {

    @Override
    public Properties getProperties(Locale locale) {
        return this.getMergedProperties(locale).getProperties();
    }

}

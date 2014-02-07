package kr.pe.ihoney.jco.restapi.common.spring;

import java.util.Locale;
import java.util.Properties;

public interface BundleMessageSource {
    Properties getProperties(Locale locale);
}

package kr.pe.ihoney.jco.restapi.service;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;

public interface LanguageSupportService {
    Map<String, String> getMessages(Locale locale, Properties properties) throws RestApiException;
}

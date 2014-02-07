package kr.pe.ihoney.jco.restapi.service.impl;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.service.LanguageSupportService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

@Slf4j
@Service
public class LanguageSupportServiceImpl implements LanguageSupportService {
    @Inject
    private MessageSourceAccessor messageSourceAccessor;
    
    @Override
    public Map<String, String> getMessages(Locale locale, Properties properties) {
        try {
            Map<String, String> messages = Maps.newTreeMap();
            for (Iterator<Object> it = properties.keySet().iterator(); it.hasNext();) {
                String key = it.next().toString();
                String message = messageSourceAccessor.getMessage(key);
                if (StringUtils.hasText(message)) {
                    messages.put(key, message);
                }
            }
            return messages;
        } catch (Exception e) {
            log.error(">> Occur Exception: {}", e.getMessage());
            throw new RestApiException(e, e.getMessage());
        }
    }

}

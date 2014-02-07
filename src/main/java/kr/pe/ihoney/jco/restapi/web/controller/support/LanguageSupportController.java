package kr.pe.ihoney.jco.restapi.web.controller.support;

import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;

import kr.pe.ihoney.jco.restapi.common.spring.BundleMessageSource;
import kr.pe.ihoney.jco.restapi.service.LanguageSupportService;

import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController = @Controller + @RequestMapping
 * 
 * @author ihoneymon
 * 
 */
@RestController
public class LanguageSupportController {
    @Inject
    private DelegatingMessageSource messageSource;
    @Inject
    private LanguageSupportService languageSupportService;

    @RequestMapping(value="/system-messages", method=RequestMethod.GET)
    public ResponseEntity<Object> getSystemMessage(Locale locale) {
        try {
            BundleMessageSource bundleMessageSource = (BundleMessageSource) messageSource.getParentMessageSource();
            Properties properties = bundleMessageSource.getProperties(locale);

            return new ResponseEntity<Object>(languageSupportService.getMessages(locale, properties), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

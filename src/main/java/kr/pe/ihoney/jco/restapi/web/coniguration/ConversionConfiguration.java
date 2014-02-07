package kr.pe.ihoney.jco.restapi.web.coniguration;

import java.util.Set;

import kr.pe.ihoney.jco.restapi.web.support.converter.LongIdTypeEntityConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import com.google.common.collect.Sets;

/**
 * 
 * @author ihoneymon
 * <a href="http://stackoverflow.com/questions/11273443/how-to-configure-spring-conversionservice-with-java-config">ConversionService</a>
 */
@Configuration
public class ConversionConfiguration {

    @Bean
    public ConversionService getConversionService() {
        FormattingConversionServiceFactoryBean bean = new FormattingConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    private Set<?> getConverters() {
        Set<GenericConverter> converters = Sets.newHashSet();
        converters.add(new LongIdTypeEntityConverter());
        return converters;
    }
}

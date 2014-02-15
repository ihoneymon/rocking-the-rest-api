package kr.pe.ihoney.jco.restapi.web.support.converter;

import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;

import com.google.common.collect.Sets;

public class StringTypeEntityConverter extends AbstractEntityConverter<String>{

    @Override
    Set<ConvertiblePair> initConvertiblePairs() {
        Set<Class<?>> targetTypes = Sets.newHashSet();
        
        Set<ConvertiblePair> convertiblePairs = Sets.newHashSet();
        for(Class<?> targetType: targetTypes) {
            convertiblePairs.add(new ConvertiblePair(Long.class, targetType));
            convertiblePairs.add(new ConvertiblePair(String.class, targetType));
        }
        return convertiblePairs;
    }

    @Override
    String convertId(Object source, TypeDescriptor sourceType) {
        return source.toString();
    }
}

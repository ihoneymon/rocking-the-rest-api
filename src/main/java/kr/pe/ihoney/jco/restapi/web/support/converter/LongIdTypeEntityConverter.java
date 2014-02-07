package kr.pe.ihoney.jco.restapi.web.support.converter;

import java.util.Set;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.User;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.NumberUtils;

import com.google.common.collect.Sets;

public class LongIdTypeEntityConverter extends AbstractEntityConverter<Long> {

    @Override
    Set<ConvertiblePair> initConvertiblePairs() {
        Set<Class<?>> targetTypes = Sets.newHashSet();
        targetTypes.add(Community.class);
        targetTypes.add(User.class);
        targetTypes.add(Post.class);

        Set<ConvertiblePair> convertiblePairs = Sets.newHashSet();
        for (Class<?> targetType : targetTypes) {
            convertiblePairs.add(new ConvertiblePair(Long.class, targetType));
        }
        return convertiblePairs;
    }

    @Override
    Long convertId(Object source, TypeDescriptor sourceType) {
        if (Long.class.equals(sourceType.getType())) {
            return (Long) source;
        } else {
            return NumberUtils.parseNumber(source.toString(), Long.class);
        }
    }

}

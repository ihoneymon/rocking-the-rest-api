package kr.pe.ihoney.jco.restapi.web.support.converter;

import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * JPA Entity 클래스 변환을 위한 추상클래스
 * 
 * @author ihoneymon
 * 
 */
@Slf4j
public abstract class AbstractEntityConverter<ID> implements GenericConverter {
    private final Set<ConvertiblePair> convertiblePairs;

    @PersistenceContext(unitName = "rocking-the-rest-api")
    private EntityManager entityManager;

    public AbstractEntityConverter() {
        this.convertiblePairs = initConvertiblePairs();
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return this.convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        log.debug("Convert[source: {}, sourceType: {}, targetType: {}]",
                new Object[] { source, sourceType, targetType });

        try {
            ID id = convertId(source, sourceType);

            Object entity = entityManager.getReference(targetType.getType(), id);

            log.debug("Found entity[type: {}, id: {}]", new Object[] { targetType.getType(), id });
            log.trace("Found entity: {}", entity);

            return entity;
        } catch (Exception e) {
            if (targetType.getAnnotation(Nullable.class) != null) {
                return null;
            }
            throw new RestApiException(e, "Fail convert: " + targetType.getType());
        }
    }

    abstract Set<ConvertiblePair> initConvertiblePairs();

    abstract ID convertId(Object source, TypeDescriptor sourceType);
}

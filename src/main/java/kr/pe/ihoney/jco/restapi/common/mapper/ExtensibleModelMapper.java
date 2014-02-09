package kr.pe.ihoney.jco.restapi.common.mapper;

import java.util.Collection;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * 모델을 지정된 DTO로 변환<br/>
 *
 * @author kim.jiheon
 */
public class ExtensibleModelMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public <S, D> void addConverter(Converter<S, D> converter) {
        modelMapper.addConverter(converter);
    }

    public <S, D> TypeMap<S, D> addMappings(PropertyMap<S, D> propertyMap) {
        return modelMapper.addMappings(propertyMap);
    }

    public <S, D> TypeMap<S, D> createTypeMap(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.createTypeMap(sourceType, destinationType);
    }

    public <S, D> TypeMap<S, D> createTypeMap(Class<S> sourceType, Class<D> destinationType, Configuration configuration) {
        return modelMapper.createTypeMap(sourceType, destinationType, configuration);
    }

    public Configuration getConfiguration() {
        return modelMapper.getConfiguration();
    }

    public <S, D> TypeMap<S, D> getTypeMap(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.getTypeMap(sourceType, destinationType);
    }

    public Collection<TypeMap<?, ?>> getTypeMaps() {
        return modelMapper.getTypeMaps();
    }

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <D> List<D> map(List<?> sources, final Class<D> destinationType) {
        return Lists.newArrayList(Lists.transform(sources, new Function<Object, D>() {
            @Override
            public D apply(Object input) {
                return modelMapper.map(input, destinationType);
            }
        }));
    }

    public <D> Iterable<D> map(Iterable<?> sources, final Class<D> destinationType) {
        return Iterables.transform(sources, new Function<Object, D>() {
            @Override
            public D apply(Object input) {
                return modelMapper.map(input, destinationType);
            }
        });
    }

    public <D> Collection<D> map(Collection<?> sources, final Class<D> destinationType) {
        return Lists.newArrayList(Iterables.transform(sources, new Function<Object, D>() {
            @Override
            public D apply(Object input) {
                return modelMapper.map(input, destinationType);
            }
        }));
    }

    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }

    public void validate() {
        modelMapper.validate();
    }

    public boolean equals(Object target) {
        return modelMapper.equals(target);
    }

    public int hashCode() {
        return modelMapper.hashCode();
    }

    public String toString() {
        return modelMapper.toString();
    }
}
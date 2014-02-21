package kr.pe.ihoney.jco.restapi.common.spring;

import java.lang.reflect.Method;
import java.util.HashSet;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * <p>
 *  <a href="http://www.codingpedia.org/ama/spring-caching-with-ehcache/#22_Configuring_the_cache_storage">Spring caching with Ehcache</a>
 * </p>
 * Default key generator. Returns {@value #NO_PARAM_KEY} if no parameters are
 * provided, the parameter itself (if primitive type) if only one is given or a
 * hash code computed from all given parameters' hash code values. Uses the
 * constant value {@value #NULL_PARAM_KEY} for any {@code null} parameters
 * given.
 * 
 * @author Costin Leau
 * @author Chris Beams
 * @since 3.1
 */
public class EnhancedDefaultKeyGenerator implements KeyGenerator {

    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;

    private static final HashSet<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 1 && isWrapperType(params[0].getClass())) {
            return (params[0] == null ? NULL_PARAM_KEY : params[0]);
        }
        if (params.length == 0) {
            return NO_PARAM_KEY;
        }
        int hashCode = 17;
        for (Object object : params) {
            hashCode = 31 * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
        }
        return Integer.valueOf(hashCode);
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static HashSet<Class<?>> getWrapperTypes() {
        HashSet<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
}
package kr.pe.ihoney.jco.restapi.web.support.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.data.redis.RedisConnectionFailureException;

public class RedisCacheInterceptor extends CacheInterceptor {
    private static final long serialVersionUID = -8966671024611602376L;

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        try {
            return super.invoke(invocation);
        } catch(RedisConnectionFailureException rcfe) {
            return invocation.proceed();
        }
    }
}

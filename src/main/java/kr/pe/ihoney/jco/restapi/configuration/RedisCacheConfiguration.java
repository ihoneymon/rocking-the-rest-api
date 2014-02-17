package kr.pe.ihoney.jco.restapi.configuration;

import kr.pe.ihoney.jco.restapi.common.spring.EnhancedDefaultKeyGenerator;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;

//@Configuration
//@EnableCaching
public class RedisCacheConfiguration implements CachingConfigurer {

	@Override
	public CacheManager cacheManager() {
		return null;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new EnhancedDefaultKeyGenerator();
	}
}

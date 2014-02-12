package kr.pe.ihoney.jco.restapi.configuration;

import kr.pe.ihoney.jco.restapi.common.spring.EnhancedDefaultKeyGenerator;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 캐시설정
 * 
 * @author ihoneymon 
 * <a href="http://javacan.tistory.com/123">EHCache의 주요 특징 및 기본 사용법</a>
 */
@Configuration
@EnableCaching
public class EhCacheConfiguration implements CachingConfigurer {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("/META-INF/ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }
    
    @Bean
    @Override
    public CacheManager cacheManager() {
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return ehCacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new EnhancedDefaultKeyGenerator();
    }
}

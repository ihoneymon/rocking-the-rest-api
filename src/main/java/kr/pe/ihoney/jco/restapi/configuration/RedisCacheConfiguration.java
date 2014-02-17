package kr.pe.ihoney.jco.restapi.configuration;

import java.util.Map;

import kr.pe.ihoney.jco.restapi.common.spring.EnhancedDefaultKeyGenerator;
import kr.pe.ihoney.jco.restapi.web.support.interceptor.RedisCacheInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.google.common.collect.Maps;

@Configuration
@EnableCaching
@Profile(value = "test")
public class RedisCacheConfiguration implements CachingConfigurer {

    @Value("${redis.cache_expire_second}")
    private Long CACHE_EXPIRE_SECONDS;
    @Value("${redis.host_name}")
    private String REDIS_HOST_NAME;
    @Value("${redis.port}")
    private int REDIS_PORT;
    @Value("${redis.password}")
    private String REDIS_PASSWORD;
    @Value("${redis.manager.default_expire_time}")
    private long REDIS_CACHE_DEFAULT_EXPIRE_TIME = 0;
    @Value("${redis.manager.use_prefix}")
    private boolean REDIS_CACHE_USE_PREFIX = false;
    @Value("${redis.use_pool}")
    private boolean REDIS_USE_POOL;
    @Value("${redis.pool_config.max_total}")
    private int JEDIS_POOL_CONFIG_MAX_TOTAL = 0;
    @Value("${redis.pool_config.max_idle}")
    private int JEDIS_POOL_CONFIG_MAX_IDLE = 0;
    @Value("${redis.pool_config.min_idle}")
    private int JEDIS_POOL_CONFIG_MIN_IDLE = 0;
    @Value("${redis.pool_config.max_wait}")
    private long JEDIS_POOL_CONFIG_MAX_WAIT = 0;

    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(cacheTemplate());
        redisCacheManager.setDefaultExpiration(REDIS_CACHE_DEFAULT_EXPIRE_TIME);
        redisCacheManager.setExpires(cacheExpires());
        redisCacheManager.setUsePrefix(REDIS_CACHE_USE_PREFIX);
        return redisCacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new EnhancedDefaultKeyGenerator();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(REDIS_HOST_NAME);
        jedisConnectionFactory.setPort(REDIS_PORT);
        jedisConnectionFactory.setPassword(REDIS_PASSWORD);
        jedisConnectionFactory.setUsePool(REDIS_USE_POOL);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean
    JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(JEDIS_POOL_CONFIG_MAX_TOTAL);
        jedisPoolConfig.setMaxIdle(JEDIS_POOL_CONFIG_MAX_IDLE);
        jedisPoolConfig.setMinIdle(JEDIS_POOL_CONFIG_MIN_IDLE);
        jedisPoolConfig.setMaxWaitMillis(JEDIS_POOL_CONFIG_MAX_WAIT);
        return jedisPoolConfig;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }

    @Bean
    StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    JacksonJsonRedisSerializer<Object> jsonRedisSerializer() {
        return new JacksonJsonRedisSerializer<Object>(Object.class);
    }

    @Bean
    JdkSerializationRedisSerializer jdkSerializationRedisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean
    AnnotationCacheOperationSource annotationCacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }

    @Bean
    RedisCacheInterceptor redisCacheInterceptor() {
        RedisCacheInterceptor redisCacheInterceptor = new RedisCacheInterceptor();
        redisCacheInterceptor.setCacheManager(cacheManager());
        redisCacheInterceptor.setCacheOperationSources(annotationCacheOperationSource());
        return redisCacheInterceptor;
    }

    @Bean(name = "cacheAdvisor")
    BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor() {
        BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor = new BeanFactoryCacheOperationSourceAdvisor();
        beanFactoryCacheOperationSourceAdvisor
                .setCacheOperationSource(annotationCacheOperationSource());
        beanFactoryCacheOperationSourceAdvisor.setAdvice(redisCacheInterceptor());
        return beanFactoryCacheOperationSourceAdvisor;
    }

    @Bean
    RedisTemplate<String, Object> cacheTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer());
        return redisTemplate;
    }

    Map<String, Long> cacheExpires() {
        Map<String, Long> cacheExpires = Maps.newConcurrentMap();
        cacheExpires.put("cache:community:detail", CACHE_EXPIRE_SECONDS);
        cacheExpires.put("cache:communities", CACHE_EXPIRE_SECONDS);
        cacheExpires.put("cache:user:detail", CACHE_EXPIRE_SECONDS);
        cacheExpires.put("cache:users", CACHE_EXPIRE_SECONDS);
        cacheExpires.put("cache:post:detail", CACHE_EXPIRE_SECONDS);
        cacheExpires.put("cache:users", CACHE_EXPIRE_SECONDS);
        return cacheExpires;
    }
}

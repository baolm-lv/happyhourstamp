package com.lvmama.vst.hhs.config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${EXPIRY_2_HOUR:7200}")
	String longer2HourSecond;
	@Value("${EXPIRY_LONGER:900}")
	String longerSecond;
	@Value("${EXPIRY_5_MINUTE:300}")
	String fineMinutes;
	@Value("${EXPIRY_MEDIUM:60}")
	String mediumSecond;
	@Value("${EXPIRY_HALF_MINUTE:30}")
	String halfMinutes;
	@Value("${EXPIRY_QUICK:5}")
	String quickSecond;

	@Bean(name = "hhsKeyGenerator")
	public KeyGenerator hhsKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	// Default CacheManager, unlimited expiration
	@Primary
	@Bean(name = "cacheManager")
	public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0),
		return cacheManager;
	}

	// expired in 5 second
	@Bean(name = "expiredQuickCacheManager")
	public CacheManager expiredQuickCacheManager(RedisTemplate<String, String> redisTemplate) {
		return this.getExpiredCacheManager(redisTemplate, this.quickSecond);
	}

	// expired in 60 second
	@Bean(name = "expiredMediumCacheManager")
	public CacheManager expiredMediumCacheManager(RedisTemplate<String, String> redisTemplate) {
		return this.getExpiredCacheManager(redisTemplate, this.mediumSecond);
	}
	// expired in 30 second
	@Bean(name = "expiredHalfMinutesCacheManager")
	public CacheManager expiredHalfMinutesCacheManager(RedisTemplate<String, String> redisTemplate) {
		return this.getExpiredCacheManager(redisTemplate, this.halfMinutes);
	}

	// expired in 5 mins
	@Bean(name = "expired5MinutesCacheManager")
	public CacheManager expiredFineMinutesCacheManager(RedisTemplate<String, String> redisTemplate) {
		return this.getExpiredCacheManager(redisTemplate, this.fineMinutes);
	}

	// expired 15 mins
	@Bean(name = "expiredLongerCacheManager")
	public CacheManager expiredLongerCacheManager(RedisTemplate<String, String> redisTemplate) {
		return this.getExpiredCacheManager(redisTemplate, this.longerSecond);
	}

	// expired in 2 hour
	@Bean(name = "expiredLonger2HourCacheManager")
	public CacheManager expiredLonger2HourCacheManager(RedisTemplate<String, String> redisTemplate) {
		return this.getExpiredCacheManager(redisTemplate, this.longer2HourSecond);
	}

	private CacheManager getExpiredCacheManager(RedisTemplate<String, String> redisTemplate, String stringSecond) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		int second = 0;
		try {
			second = Integer.parseInt(stringSecond);
		} catch (Exception e) {
		}
		cacheManager.setDefaultExpiration(second);
		return cacheManager;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, String> template = new HhsRedisTemplate<>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}

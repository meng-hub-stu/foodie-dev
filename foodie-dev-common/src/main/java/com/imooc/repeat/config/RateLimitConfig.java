package com.imooc.repeat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 限流注解配置类
 *
 * @author DuChao
 * @date 2022/1/7 11:11 AM
 */
@Configuration
public class RateLimitConfig {

	/**
	 * 限流脚本
	 */
	@Bean
	public RedisScript<Long> limitRedisScript() {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts.redis/limit.lua")));
		redisScript.setResultType(Long.class);
		return redisScript;
	}

}

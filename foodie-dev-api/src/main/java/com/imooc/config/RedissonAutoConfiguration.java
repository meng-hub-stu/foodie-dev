package com.imooc.config;

import org.redisson.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mengdl
 * @date 2022/01/26
 */
@Configuration
@ConditionalOnClass(Config.class)
public class RedissonAutoConfiguration {

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    RedissonClient redissonSingle() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

}

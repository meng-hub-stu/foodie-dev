package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Mengdl
 * @date 2021/11/01
 */
@SpringBootApplication/*(exclude = {SecurityAutoConfiguration.class})*/
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = {"com.imooc.mapper"})
@ComponentScan(basePackages = {"com.imooc", "org.n3r.idworker"})
//开启定时器
@EnableScheduling
//开启异步处理
//@EnableAsync
@EnableCaching
//开启spring-session
//@EnableRedisHttpSession
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

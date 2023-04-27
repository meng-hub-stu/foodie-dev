package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Mengdl
 * @date 2021/11/01
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = {"com.imooc.mapper"})
@ComponentScan(basePackages = {"com.imooc", "org.n3r.idworker"})
public class SSoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSoApplication.class, args);
    }
}

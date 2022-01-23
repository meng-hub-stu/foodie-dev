package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author Mengdexin
 * @date 2022 -01 -23 -21:11
 */
@SpringBootApplication/*(exclude = {SecurityAutoConfiguration.class})*/
@MapperScan(basePackages = {"com.imooc.mapper"})
@ComponentScan(basePackages = {"com.imooc", "org.n3r.idworker"})
public class FsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FsApplication.class, args);
    }
    
}

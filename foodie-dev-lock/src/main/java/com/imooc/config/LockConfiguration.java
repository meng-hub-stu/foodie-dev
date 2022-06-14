package com.imooc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Mengdx
 * @Date 2022/06/13
 **/
@Configuration
@ComponentScan("com.imooc")
@MapperScan({"com.imooc.mapper.**"})
public class LockConfiguration {
}

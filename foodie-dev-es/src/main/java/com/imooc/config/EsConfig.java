package com.imooc.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author Mengdexin
 * @date 2021 -12 -31 -21:49
 */
@Configuration
public class EsConfig {

    @PostConstruct
    void init(){
        //解决netty引发的issue的问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}

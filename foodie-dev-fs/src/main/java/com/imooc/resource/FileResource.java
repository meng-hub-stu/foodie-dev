package com.imooc.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author Mengdexin
 * @date 2022 -01 -23 -21:54
 */
@Component
@ConfigurationProperties(prefix = "fdfs")
@PropertySource("classpath:file.properties")
public class FileResource {

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}

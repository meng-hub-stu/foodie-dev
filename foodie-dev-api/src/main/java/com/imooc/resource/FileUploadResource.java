package com.imooc.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author Mengdexin
 * @date 2021 -11 -18 -22:40
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-dev.properties")
public class FileUploadResource {

    private String fileUserFaceUrl;

    private String fileServerUrl;

    public String getFileServerUrl() {
        return fileServerUrl;
    }

    public void setFileServerUrl(String fileServerUrl) {
        this.fileServerUrl = fileServerUrl;
    }

    public String getFileUserFaceUrl() {
        return fileUserFaceUrl;
    }

    public void setFileUserFaceUrl(String fileUserFaceUrl) {
        this.fileUserFaceUrl = fileUserFaceUrl;
    }
}

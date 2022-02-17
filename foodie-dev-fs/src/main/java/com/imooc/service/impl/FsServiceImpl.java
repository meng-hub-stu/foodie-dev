package com.imooc.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.imooc.resource.FileResource;
import com.imooc.service.FsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author Mengdexin
 * @date 2022 -01 -23 -21:13
 */
@Service
public class FsServiceImpl implements FsService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FileResource fileResource;

    @Override
    public String upload(MultipartFile file, String fileExtName) throws Exception {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                file.getSize(), fileExtName, null);
        String fullPath = storePath.getFullPath();
        return fullPath;
    }

    @Override
    public String uploadOSS(MultipartFile file, String userId, String fileExtName) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                fileResource.getEndpoint(),
                    fileResource.getAccessKeyId(),
                        fileResource.getAccessKeySecret());
        InputStream inputStream = file.getInputStream();
        // 创建PutObject请求。
        String fileName = fileResource.getObjectName() + "/" + userId + "/" + userId + "." + fileExtName;
        ossClient.putObject(fileResource.getBucketName(), fileName, inputStream);
        ossClient.shutdown();
        return fileName;
    }

}

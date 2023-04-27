package com.imooc.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.imooc.service.FsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Mengdexin
 * @date 2022 -01 -23 -21:13
 */
@Service
@Slf4j
public class FsServiceImpl implements FsService{

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String upload(MultipartFile file, String fileExtName) throws Exception {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                file.getSize(), fileExtName, null);
        String fullPath = storePath.getFullPath();
        return fullPath;
    }

    @Override
    public void download(String groupName, String fullPath, String fileName, HttpServletResponse httpServletResponse){
        DownloadByteArray callback = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(groupName, fullPath, callback);
        httpServletResponse.reset();
        httpServletResponse.setContentType("application/x-download");
        httpServletResponse.addHeader("Content-Disposition" ,"attachment;filename=\"" +fileName+ "\"");
        try {
            httpServletResponse.getOutputStream().write(bytes);
            httpServletResponse.getOutputStream().close();
        } catch (IOException e) {
            log.error("下载失败->{}", fullPath);
            e.printStackTrace();
        }
    }

}

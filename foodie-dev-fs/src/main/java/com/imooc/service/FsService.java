package com.imooc.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Mengdexin
 * @date 2022 -01 -23 -21:11
 */
public interface FsService {
    /**
     * 上传文件
     * @param file
     * @param fileExtName
     * @return
     */
    String upload(MultipartFile file, String fileExtName) throws Exception;


    /**
     * 下载文件
     * @param groupName 分组的名称
     * @param fullPath 路径
     * @param fileName 下载的名称
     * @param httpServletResponse 响应信息
     */
    void download(String groupName, String fullPath, String fileName, HttpServletResponse httpServletResponse);

}

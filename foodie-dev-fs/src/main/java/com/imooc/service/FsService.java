package com.imooc.service;

import org.springframework.web.multipart.MultipartFile;

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
     * 上传文件oss
     * @param file 文件
     * @param userId 用户id
     * @param fileExtName 文件后缀名称
     * @return 返回文件名称
     * @throws Exception
     */
    String uploadOSS(MultipartFile file, String userId, String fileExtName) throws Exception;

}

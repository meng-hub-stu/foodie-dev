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




}

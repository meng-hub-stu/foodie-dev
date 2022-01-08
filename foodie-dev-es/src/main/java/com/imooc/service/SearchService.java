package com.imooc.service;

import com.imooc.utils.PagedGridResult;

/**
 * @Author Mengdexin
 * @date 2022 -01 -08 -16:29
 */
public interface SearchService {
    /**
     * 查询数据
     * @param keywords 搜索内容
     * @param sort 排序
     * @param page 页码
     * @param pageSize 每页多少条
     * @return 返回结果
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

}

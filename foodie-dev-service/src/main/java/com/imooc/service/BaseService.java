package com.imooc.service;

import com.github.pagehelper.PageInfo;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @Author Mengdexin
 * @date 2021 -11 -21 -18:35
 */
public class BaseService {
    /**
     * 进行分页
     * @param page 页码
     * @param params 数据
     * @return 分页对象
     */
    public PagedGridResult setterPagedGridResult(Integer page, List<?> params){
        PageInfo<?> pageList = new PageInfo<>(params);
        PagedGridResult result = new PagedGridResult();
        result.setPage(page);
        result.setRows(params);
        result.setTotal(pageList.getPages());
        result.setRecords(pageList.getTotal());
        return result;
    }

}

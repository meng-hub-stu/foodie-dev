package com.imooc.service;

import com.imooc.pojo.Stu;
import com.imooc.utils.PagedGridResult;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:14
 */
public interface StuService {

    /**
     * 获取数据
     * @param id
     * @return
     */
   Stu getStu(Integer id);

    /**
     * 分页获取数据
     * @param page 页码
     * @param row 行数
     * @return 返回结果
     */
    PagedGridResult getPageList(Integer page, Integer row);

}

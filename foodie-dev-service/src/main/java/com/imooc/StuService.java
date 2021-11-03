package com.imooc;

import com.imooc.pojo.Stu;

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

}

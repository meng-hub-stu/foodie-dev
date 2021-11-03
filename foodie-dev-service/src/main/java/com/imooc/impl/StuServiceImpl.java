package com.imooc.impl;

import com.imooc.StuService;
import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:15
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private  StuMapper stuMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Stu getStu(Integer id) {
        return stuMapper.selectByPrimaryKey(id);
    }
}

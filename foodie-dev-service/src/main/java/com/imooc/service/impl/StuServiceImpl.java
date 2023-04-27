package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.service.StuService;
import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.utils.PagedGridResult;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:15
 */
@Service
//@CacheNamespace
public class StuServiceImpl implements StuService {

    @Autowired
    private  StuMapper stuMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Stu getStu(Integer id) {
        Stu stu = stuMapper.selectByPrimaryKey(id);
        System.out.println(stu);
        return stu;
    }

    @Override
    public PagedGridResult getPageList(Integer page, Integer row) {
        PageHelper.startPage(page, row);
        List<Stu> stus = stuMapper.selectAll();
        //将分页的数据进行获取
        PageInfo<?> pageList = new PageInfo<>(stus);
        //封装返回对象
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(stus);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

}

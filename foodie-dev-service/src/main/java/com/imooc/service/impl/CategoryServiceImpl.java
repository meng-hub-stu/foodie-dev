package com.imooc.service.impl;

import com.imooc.mapper.CategoryCustomMapper;
import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 商品分类
 * @Author Mengdexin
 * @date 2021 -11 -09 -22:29
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryCustomMapper categoryCustomMapper;

    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<CategoryVO> querySubCat(Integer rootId) {
        return categoryCustomMapper.getSubCatList(rootId);
    }
}

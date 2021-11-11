package com.imooc.service.impl;

import com.imooc.mapper.CategoryCustomMapper;
import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemVO;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        return categoryMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<CategoryVO> querySubCat(Integer rootId) {
        return categoryCustomMapper.getSubCatList(rootId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<NewItemVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("rootCatId", rootCatId);
        return categoryCustomMapper.getSixNewItemsLazy(map);
    }

}

package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemVO;

import java.util.List;
/**
 * 分类服务层
 * @Author Mengdexin
 * @date 2021 -11 -09 -22:24
 */
public interface CategoryService {
    /**
     * 查询商品一级分类
     * @return 返回商品分类信息
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 获取二级和三级的分类
     * @param rootId 一级的id
     * @return 返回结果
     */
    List<CategoryVO> querySubCat(Integer rootId);

    /**
     * 查询每一个一级分类下的商品
     * @param rootCatId 一级分类的id
     * @return 返回结果
     */
    List<NewItemVO> getSixNewItemsLazy(Integer rootCatId);

}

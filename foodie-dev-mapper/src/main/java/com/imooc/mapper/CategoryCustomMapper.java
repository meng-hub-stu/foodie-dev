package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义mapper接口
 * @Author Mengdexin
 * @date 2021 -11 -09 -23:00
 */
public interface CategoryCustomMapper {
    /**
     * 获取商品分类的二级和三级的数据
     * @param rootId 一级的目录
     * @return 返回的结果
     */
    List<CategoryVO> getSubCatList(@Param(value = "rootId") Integer rootId);

    /**
     * 一级分类下的最新的几条商品
     * @param map 一级分类的id
     * @return 返回结果
     */
    List<NewItemVO> getSixNewItemsLazy(@Param(value = "param") Map<String, Object> map);

}

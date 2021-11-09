package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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

}

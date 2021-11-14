package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品搜索数据
 * @author Mengdl
 * @date 2021/11/11
 */
public interface ItemsMapperCustom {

    /**
     * 商品评价分页列表
     * @param param 入参
     * @return 评价结果
     */
    List<ItemCommentVO> queryCommentByPage(@Param(value = "param") Map<String, Object> param);

    /**
     * 商品搜索列表
     * @param param 入参
     * @return 返回结果
     */
    List<SearchItemsVO> searchItems(@Param(value = "param") Map<String, Object> param);

    /**
     * 查询最新的商品规格
     * @param specIdsList 规格ids
     * @return 返回结果
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param(value = "param") List<String> specIdsList);

    /**
     * 创建订单
     * @param specId 商品规格id
     * @param pendingCounts 购买的数量
     * @return 返回结果
     */
    int decreaseItemSpecStork(@Param("specId") String specId, @Param("pendingCounts") int pendingCounts);

}

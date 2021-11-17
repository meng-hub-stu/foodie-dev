package com.imooc.mapper.center;

import org.apache.ibatis.annotations.Param;

/**
 * @author Mengdl
 * @date 2021/11/17
 */
public interface CenterMapper {

    /**
     * 我的订单的数量
     * @param userId 用户id
     * @param type 商品的状态
     * @return 数量
     */
    Integer selectWaitCounts(@Param("userId") String userId, @Param("type") Integer type);

    /**
     * 待评价的数量
     * @param userId 用户id
     * @param comment
     * @param type 商品的状态
     * @return
     */
    Integer selectWaitCommentCounts(@Param("userId") String userId,  @Param("type") Integer type, @Param("comment") Integer comment);

}

package com.imooc.mapper;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.vo.center.MyOrderVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author Mengdl
 * @date 2021/11/17
 */
public interface OrdersMapperCustom {

    /**
     * 我的订单的数量
     * @param userId 用户id
     * @param type 商品的状态
     * @param comment 是否评价
     * @return 数量
     */
    Integer getMyOrderStatusCounts(@Param("userId") String userId, @Param("type") Integer type, @Param("comment") Integer comment);

    /**
     * 查询个人订单
     * @param userId 用户id
     * @param orderStatus 订单的状态
     */
    List<MyOrderVO> selectOrderData(@Param(value = "userId") String userId, @Param(value = "orderStatus") String orderStatus);

    /**
     * 订单的动向
     * @param userId 用户id
     * @return 返回数据
     */
    List<OrderStatus> quertMyOrdersTrend(@Param(value = "userId") String userId);

}

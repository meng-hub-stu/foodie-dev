package com.imooc.service.center;

import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.center.OrderStatusCountVO;
import com.imooc.utils.PagedGridResult;

/**
 * @Author Mengdexin
 * @date 2021 -11 -20 -13:44
 */
public interface MyOrdersService {

    /**
     * 查询订单信息
     * @param userId 用户id
     * @param orderStatus 订单的状态
     * @param page 页码
     * @param pageSize 页码数量
     * @return 返回分页结果
     */
    PagedGridResult queryOrderData(String userId, String orderStatus, Integer page, Integer pageSize);

    /**
     * 商品修改状态
     * @param orderId 商品id
     * @param type 状态
     * @return 返回结果
     */
    boolean updateOrderStatusByOrderId(String orderId, Integer type);

    /**
     * 查询我的订单
     * @param userId 用户ID
     * @param orderId 订单那id
     * @return 订单数据
     */
    Orders queryMyOrders(String userId, String orderId);

    /**
     * 用户删除订单
     * @param userId 用户id
     * @param orderId 商品的id
     * @return 返回结果
     */
    boolean deleteOrderByUserIdAndOrderId(String userId, String orderId);

    /**
     * 我的订单动向
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 每页数量
     * @return
     */
    PagedGridResult queryMyOrdersTrend(String userId, Integer page, Integer pageSize);

    /**
     * 查询个人订单的数量
     * @return 返回结果对象
     * @param userId 用户id
     */
    OrderStatusCountVO queryUserOrdersStatusCounts(String userId);

}

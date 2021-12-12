package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.OrderBO;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.pojo.vo.OrderVO;

import java.util.List;

/**
 * 订单相关的接口类
 * @Author Mengdexin
 * @date 2021 -11 -12 -23:38
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderBO 入参数据
     * @param shopcartList 购物车数据
     */
    OrderVO createOrder(OrderBO orderBO, List<ShopcartBO> shopcartList);

    /**
     *  修改订单
     * @param orderBO 订单对象
     */
    void updateOrder(OrderBO orderBO);

    /**
     * 删除订单
     * @param userId 用户id
     * @param orderId 订单的id
     */
    void deletedOrder(String userId, String orderId);

    /**
     * 支付中心进行回调
     * @param merchantOrderId 订单的id
     * @param type 修改的状态
     */
    void notifyMerchantOrderPaid(String merchantOrderId, Integer type);

    /**
     * 查询订单的状态
     * @param orderId 订单的id
     * @return 返回结果对象
     */
    OrderStatus queryOrderStatus(String orderId);

    /**
     * 自动关闭订单
     */
    void autoCloseOrder();

}

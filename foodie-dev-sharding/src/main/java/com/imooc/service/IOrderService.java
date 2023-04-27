package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Order;

/**
 * @author Mengdl
 * @date 2022/06/14
 */
public interface IOrderService extends IService<Order> {
    /**
     * 创建订单
     * @return 返回结果
     */
    boolean createOrder();

    /**
     * 验证分布式锁
     * @param order 信息数据
     * @param i
     * @return 返回信息
     */
    boolean createOrderByThread(Order order, int i);

}

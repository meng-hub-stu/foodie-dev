package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Order;

/**
 * @Author mengdexin
 * @Date 2022:06:13:23:24
 **/
public interface IOrderService extends IService<Order> {

    /**
     * 创建订单
     * @return 创建结果
     */
    boolean createOrder();
}

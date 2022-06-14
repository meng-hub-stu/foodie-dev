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

}

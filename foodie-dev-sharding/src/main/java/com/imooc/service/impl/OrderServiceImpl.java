package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.entity.Order;
import com.imooc.entity.OrderItem;
import com.imooc.entity.Product;
import com.imooc.mapper.OrderItemMapper;
import com.imooc.mapper.OrderMapper;
import com.imooc.mapper.ProductMapper;
import com.imooc.service.IOrderService;
import com.mengdx.annotation.RateLimiter;
import com.mengdx.annotation.RedisLock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.mengdx.entity.enums.LockModel._RED_LOCK;

/**
 * @author Mengdl
 * @date 2022/06/14
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final OrderItemMapper orderItemMapper;

    private final ProductMapper productMapper;

    private static final Long PRODUCT_ID = 123L;

    private static final Integer count = 1;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @RedisLock(lockPrefix = "order", lockParameter = "#order.address+\":\"+#i+\":\"+#order.totalPrice")
    @RateLimiter(timeout = 5L)
    public /*synchronized*/ boolean createOrder() {
        //1.查询产品的数量
        Product product = productMapper.selectById(PRODUCT_ID);
        System.out.println(String.format("当前线程名称：%s,当前库存数：%s",Thread.currentThread().getName(), product.getCount()));
        if (count > product.getCount()) {
            throw new RuntimeException("库存不够");

        }
        //2.进行减库存
        product.setCount(product.getCount() - count);
        //3.更新产品的数据
        productMapper.updateById(product);
        //4.更定订单的数据
        Date date = new Date();
        Order order = Order.builder()
                .address("北京市顺义区")
                .totalPrice(product.getPrice())
                .build();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        baseMapper.insert(order);
        OrderItem orderItem = OrderItem.builder()
                .orderId(order.getId())
                .count(count)
                .productId(PRODUCT_ID)
                .price(product.getPrice())
                .build();
        orderItem.setCreateTime(date);
        orderItem.setUpdateTime(date);
        orderItemMapper.insert(orderItem);
        return true;
    }

    @Override
    @RedisLock(lockPrefix = "order", lockParameter = "#order.address+\":\"+#i+\":\"+#order.totalPrice", lockModel = _RED_LOCK)
    public boolean createOrderByThread(Order order, int i) {
        return false;
    }

}

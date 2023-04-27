package com.imooc.controller;

import com.imooc.entity.Order;
import com.imooc.service.IOrderService;
import com.imooc.utils.IMOOCJSONResult;
import com.mengdx.utils.RedisLockUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试订单的并发
 * @author Mengdl
 * @date 2022/06/14
 */
@RestController
@RequestMapping(value = "order")
@Api(value = "订单管理", tags = "订单管理")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final IOrderService orderService;
    private final RedisTemplate redisTemplate;

    @PostMapping(value = "threadOrder")
    @ApiOperation(value = "模拟多线程创建订单", notes = "无条件")
    public IMOOCJSONResult createThreadOrder(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                orderService.createOrder("1");
            });
        }
        return IMOOCJSONResult.ok();
    }
    @PostMapping(value = "singleOrder")
    @ApiOperation(value = "模拟单线程创建订单", notes = "无条件")
    public IMOOCJSONResult createSingleOrder(@RequestParam String i){
        orderService.createOrder(i);
        return IMOOCJSONResult.ok(true);
    }

    @PostMapping(value = "limiter")
    @ApiOperation(value = "限流", notes = "限流")
    public IMOOCJSONResult limiter(){
        Order order = new Order();
        order.setAddress("123");
        order.setTotalPrice(new BigDecimal(10));
        orderService.createOrderByThread(order, 5);
        return IMOOCJSONResult.ok(true);
    }

    @GetMapping(value = "lock")
    @ApiOperation(value = "自测写的分布式锁方法", notes = "无条件")
    public IMOOCJSONResult singleThread(){
        log.info("进入到方法");
        try (RedisLockUtil lock = new RedisLockUtil(redisTemplate, "redis-123", 30)) {
            if (lock.getLock()) {
                log.info("获取到分布式锁");
                Thread.sleep(15000);
                log.info("执行业务逻辑结束");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("执行方法结束");
        return IMOOCJSONResult.ok();
    }

}

package com.imooc.controller;

//import com.imooc.repeat.RedisRepeat;
import com.imooc.service.IOrderService;
//import com.imooc.utils.IMOOCJSONResult;
import com.mengdx.annotation.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class OrderController {

    private final IOrderService orderService;

    @PostMapping(value = "threadOrder")
    @ApiModelProperty(value = "模拟多线程创建订单", notes = "无条件")
    public boolean createThreadOrder(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(orderService::createOrder);
        }
//        return IMOOCJSONResult.ok();
        return true;
    }
    @PostMapping(value = "singleOrder")
    @ApiModelProperty(value = "模拟单线程创建订单", notes = "无条件")
    public boolean createSingleOrder(){
        orderService.createOrder();
//        return IMOOCJSONResult.ok(true);
        return true;
    }

    @PostMapping(value = "limiter")
    @ApiModelProperty(value = "限流", notes = "限流")
    @RateLimiter(timeout = 5L)
//    @RedisRepeat(timeout = 5L)
    public boolean limiter(){
//        return IMOOCJSONResult.ok(true);
        return true;
    }

}

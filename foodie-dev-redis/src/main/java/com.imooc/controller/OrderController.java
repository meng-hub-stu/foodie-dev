package com.imooc.controller;

import com.imooc.entity.Order;
import com.imooc.service.IOrderService;
import com.imooc.utils.IMOOCJSONResult;
import com.mengdx.cache.CascadeEvictKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
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
public class OrderController {

    private final IOrderService orderService;

    @PostMapping(value = "threadOrder")
    @ApiOperation(value = "模拟多线程创建订单", notes = "无条件")
    public IMOOCJSONResult createThreadOrder(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(orderService::createOrder);
        }
        return IMOOCJSONResult.ok();
    }
    @PostMapping(value = "singleOrder")
    @ApiOperation(value = "模拟单线程创建订单", notes = "无条件")
    public IMOOCJSONResult createSingleOrder(){
        orderService.createOrder();
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


    @GetMapping(value = "{id}}")
    @ApiOperation(value = "查看订单数据", notes = "订单id")
    @Cacheable(cacheNames = "order", keyGenerator = "tenantKeyGenerator")
    public IMOOCJSONResult queryOrder(@PathVariable(value = "id") @Positive Long id, @RequestParam String bbb){
        Order order = orderService.getById(id);
        return IMOOCJSONResult.ok(order);
    }


    @DeleteMapping(value = "{id}}")
    @ApiOperation(value = "查看订单数据", notes = "订单id")
//    @CacheEvict(cacheNames = "order", key = "#id")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "order", keyGenerator = "tenantCascadeKeyGenerator")
            }
    )
    @CascadeEvictKey({"id"})
    public IMOOCJSONResult deleteOrder(@PathVariable(value = "id") @Positive Long id){
        boolean result = orderService.removeById(id);
        return IMOOCJSONResult.ok(result);
    }

}

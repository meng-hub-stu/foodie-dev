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

/**
 * @author Mengdl
 * @date 2022/06/23
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "cache")
@Api(value = "缓存管理", tags = "缓存管理")
public class CacheController {

    private final IOrderService orderService;


    @GetMapping(value = "{id}}")
    @ApiOperation(value = "获取订单数据", notes = "订单id")
//    @Cacheable(cacheNames = "order", key = "#id")
    @Cacheable(cacheNames = "order", keyGenerator = "tenantPrimaryKeyGenerator")
    public IMOOCJSONResult queryOrder(@PathVariable(value = "id") @Positive Long id){
        Order order = orderService.getById(id);
        return IMOOCJSONResult.ok(order);
    }


    @DeleteMapping(value = "{id}}")
    @ApiOperation(value = "更新订单数据", notes = "订单id")
    @CacheEvict(cacheNames = "order", keyGenerator = "tenantPrimaryKeyGenerator")
    @CascadeEvictKey({"id", "queryOrder"})
//    @Caching(
//            evict = {
//                    @CacheEvict(cacheNames = "order", key = "#id")
//            }
//    )
//    @CascadeEvictKey({"id"})
    public IMOOCJSONResult deleteOrder(@PathVariable(value = "id") @Positive Long id){
        Order order = orderService.getById(id);
        order.setAddress("祖国");
        boolean result = orderService.updateById(order);
        return IMOOCJSONResult.ok(result);
    }

}

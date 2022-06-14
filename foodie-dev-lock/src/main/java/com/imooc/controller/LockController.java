package com.imooc.controller;

import com.imooc.service.IOrderService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Mengdx
 * @Date 2022/06/14
 **/
@RestController
@RequestMapping(value = "order")
@Api(value = "订单管理", tags = "订单管理")
@AllArgsConstructor
public class LockController {

    private final IOrderService orderService;

    @PostMapping(value = "create")
    @ApiModelProperty(value = "创建订单", notes = "无条件")
    public IMOOCJSONResult createOrder(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
       /* for (int i = 0; i < 5; i++) {
            executorService.execute(orderService::createOrder);
        }*/
        return IMOOCJSONResult.ok(orderService.createOrder());
    }

}

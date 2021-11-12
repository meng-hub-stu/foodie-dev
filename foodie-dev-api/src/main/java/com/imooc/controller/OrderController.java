package com.imooc.controller;

import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 订单控制层
 * @author Mengdl
 * @date 2021/11/11
 */
@Api(value = "订单管理", tags = {"订单相关接口"})
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @PostMapping(value = "create")
    @ApiOperation(value = "创建订单", notes = "订单参数", httpMethod = "POST")
    public IMOOCJSONResult create(@RequestBody Map<String, Object> map) {
        return null;

    }



}

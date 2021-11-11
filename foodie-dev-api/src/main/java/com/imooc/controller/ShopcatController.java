package com.imooc.controller;

import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 购物车控制层
 * @author Mengdl
 * @date 2021/11/11
 */
@RestController
@RequestMapping(value = "shopcat")
@Api(value = "购物车管理", tags = {"购物车相关接口"})
public class ShopcatController {

    @PostMapping
    @ApiOperation(value = "添加购物车", notes = "用户数据和商品数据", httpMethod = "POST")
    public IMOOCJSONResult add(
            @RequestParam(value = "userId") String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response

    ) {
        //前端用户再登录的情况下，将购物车的数据放在redis中
        return IMOOCJSONResult.ok();
    }

    @DeleteMapping
    @ApiOperation(value = "删除购物车的数据", notes = "删除购物车的数据", httpMethod = "DELETE")
    public IMOOCJSONResult del(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "itemSpecId") String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return IMOOCJSONResult.ok();
    }
}

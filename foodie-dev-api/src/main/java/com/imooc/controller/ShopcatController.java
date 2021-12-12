package com.imooc.controller;

import com.google.common.collect.Lists;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 购物车控制层
 * @author Mengdl
 * @date 2021/11/11
 */
@RestController
@RequestMapping(value = "shopcart")
@Api(value = "购物车管理", tags = {"购物车相关接口"})
public class ShopcatController extends BaseController{

    @Autowired
    private RedisOperator redisOperator;


    @PostMapping(value = "add")
    @ApiOperation(value = "添加购物车", notes = "用户数据和商品数据", httpMethod = "POST")
    public IMOOCJSONResult add(
            @RequestParam(value = "userId") String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response

    ) {
        System.out.println(shopcartBO);
        //前端用户在登录的情况下，将购物车的数据同步放在redis中
        String shopCatResult = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopcartList = null;
        if(isNotBlank(shopCatResult)){
            shopcartList = JsonUtils.jsonToList(shopCatResult, ShopcartBO.class);
            boolean isHaving = false;
            for (ShopcartBO sc : shopcartList){
                if(sc.getSpecId().equals(shopcartBO.getSpecId())){
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if(!isHaving){
                shopcartList.add(shopcartBO);
            }
        } else {
            shopcartList = Lists.newArrayList();
            shopcartList.add(shopcartBO);
        }
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "del")
    @ApiOperation(value = "删除购物车的数据", notes = "删除购物车的数据", httpMethod = "DELETE")
    public IMOOCJSONResult del(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "itemSpecId") String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        //用户在登录的状态下，删除redis中的购物车的数据，更新购物车的数据
        String shopCatResult = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopcartList = null;
        if(isNotBlank(shopCatResult)){
            shopcartList = JsonUtils.jsonToList(shopCatResult, ShopcartBO.class);
            for (ShopcartBO sc : shopcartList){
                if(sc.getSpecId().equals(itemSpecId)){
                    shopcartList.remove(sc);
                    break;
                }
            }
            //覆盖现有的购物车数据
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));
        }
        return IMOOCJSONResult.ok();
    }
}

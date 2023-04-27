package com.imooc.controller;

import com.google.common.collect.Lists;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:08
 */
@RestController
@RequestMapping("passport")
@Api(value = "用户管理模块", tags = {"用户管理模块相关接口"})
public class PassportController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private RedisOperator redisOperator;

    @GetMapping(value = "usernameIsExist")
    @ApiOperation(value = "校验用户名称", notes = "传入用户名", httpMethod = "GET")
    public IMOOCJSONResult userNameIsExist(@RequestParam String username){
        if(isBlank(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        boolean flag = userService.queryUsernameIsExist(username);
        if(flag){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "regist")
    @ApiOperation(value = "用户注册", notes = "传入数据")
    public IMOOCJSONResult regist(@Validated @RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        if(!userBO.getConfirmPassword().equals(userBO.getPassword())){
            return IMOOCJSONResult.errorMsg("密码和确认密码不一致！");
        }
        boolean flag = userService.queryUsernameIsExist(userBO.getUsername());
        if(flag){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        if(userBO.getPassword().length() < 6){
            return IMOOCJSONResult.errorMsg("密码的长度不能少于六位");
        }
        Users users = userService.createUser(userBO);
        if(users == null){
            return IMOOCJSONResult.errorMsg("用户注册失败");
        }

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(createUserToken(users)), true);
        //将用户信息的数据放在redis中
        // 同步购物车数据
        sysShopCart(users.getId(), request, response);
        return IMOOCJSONResult.ok(users);
    }

    /**
     * redis和cookie的购物车数据进行同步
     * @param userId 用户id
     * @param request 请求
     * @param response 响应
     */
    private void sysShopCart(String userId, HttpServletRequest request,
                             HttpServletResponse response){
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);
        if(isBlank(shopcartJsonRedis)){
            //redis中数据为空，cookie不为空，将cookie的数据放在redis中
            if(isNotBlank(shopcartStrCookie)){
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            }
        } else {
            //redis中数据不为空，cookie的数据不为空，以cookie中的数据为主，重置redis的数据
            if(isNotBlank(shopcartStrCookie)){
                /**
                 * 1.已经存在的，将cookie中对应的数量，覆盖redis
                 * 2.将商品更新为待删除，统一放在一个待删除的list中
                 * 3.cookie清理待删除的数据，
                 * 4.合并redis和cookie中的数据
                 * 5.更新redis和cookie中的数据
                 */
                List<ShopcartBO> shopcartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopcartBO.class);
                List<ShopcartBO> pendingDeletedData = Lists.newArrayList();
                for(ShopcartBO redisShopcart : shopcartListRedis){
                    for (ShopcartBO cookieShopcart : shopcartListCookie){
                        if(redisShopcart.getSpecId().equals(cookieShopcart.getSpecId())){
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            pendingDeletedData.add(cookieShopcart);
                        }
                    }

                }
                //cookie进行清理待删除的数据
                shopcartListCookie.removeAll(pendingDeletedData);
                //合并redis和cookie中的数据
                shopcartListRedis.addAll(shopcartListCookie);
                //更新redis和cookie的数据
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartListRedis));
            } else {
                //redis中的数据不为空，cookie的数据为空
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            }
        }
    }

    @PostMapping(value = "login")
    @ApiOperation(value = "用户登录", notes = "传入对象", httpMethod = "POST")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        if(isBlank(userBO.getUsername())
                || isBlank(userBO.getPassword())){
            return IMOOCJSONResult.errorMsg("用户名和密码不能为空");
        }
        Users users = null;
        try {
            users = userService.userLogin(userBO.getUsername(), MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(users == null){
            return IMOOCJSONResult.errorMsg("用户不存在");
        }
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(createUserToken(users)), true);
        sysShopCart(users.getId(), request, response);
        return IMOOCJSONResult.ok(users);
    }

    @PostMapping(value = "logout")
    @ApiOperation(value = "推出登录", notes = "传入用户id", httpMethod = "POST")
    public IMOOCJSONResult logout(@RequestParam(value = "userId") String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        //清空购物车等
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);
        //清除redis中用户信息
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        //清空cokile
        CookieUtils.deleteCookie(request, response, "user");

        return IMOOCJSONResult.ok();
    }

}

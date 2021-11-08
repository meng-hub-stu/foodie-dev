package com.imooc.controller;

import com.imooc.UserService;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:08
 */
@RestController
@RequestMapping("passport")
@Api(value = "用户管理模块", tags = {"用户管理模块"})
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "usernameIsExist")
    @ApiOperation(value = "校验用户名称", notes = "传入用户名", httpMethod = "GET")
    public IMOOCJSONResult userNameIsExist(@RequestParam String username){
        if(StringUtils.isBlank(username)){
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
    public IMOOCJSONResult regist(@Validated @RequestBody UserBO userBO){
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
        Users user = userService.createUser(userBO);
        if(user == null){
            return IMOOCJSONResult.errorMsg("用户注册失败");
        }
        return IMOOCJSONResult.ok(user);
    }

    @PostMapping(value = "login")
    @ApiOperation(value = "用户登录", notes = "传入对象", httpMethod = "POST")
    public IMOOCJSONResult login(@RequestBody UserBO userBO){
        if(StringUtils.isBlank(userBO.getUsername())
                || StringUtils.isBlank(userBO.getPassword())){
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
        return IMOOCJSONResult.ok();
    }

}

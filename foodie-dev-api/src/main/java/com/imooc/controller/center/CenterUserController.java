package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户控制类
 * @Author Mengdexin
 * @date 2021 -11 -16 -22:37
 */
@RestController
@Api(value = "用户数据", tags = {"用户相关接口"})
@RequestMapping(value = "userInfo")
public class CenterUserController {

    @Autowired
    private CenterService centerService;

    @PostMapping(value = "update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    public IMOOCJSONResult update(@ApiParam(value = "userId", name = "用户id", required = true)
                                  @RequestParam String userId,
                                  @RequestBody CenterUserBO centerUserBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        Users usersResult = centerService.updateUserInfo(userId, centerUserBO);
        //将敏感信息进行清空或者等等
        // TODO 以后会用redis和token等概念
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersResult), true);
        return IMOOCJSONResult.ok();
    }

}

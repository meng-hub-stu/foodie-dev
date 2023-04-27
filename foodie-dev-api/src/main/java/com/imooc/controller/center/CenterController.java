package com.imooc.controller.center;

import com.imooc.service.center.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户个人中心
 * @Author Mengdexin
 * @date 2021 -11 -16 -22:18
 */
@Api(value = "用户个人中心", tags = {"用户个人中心相关接口"})
@RestController
@RequestMapping(value = "center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @GetMapping(value = "userInfo")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息", httpMethod = "GET")
    public IMOOCJSONResult detail(@ApiParam(name = "userId", value = "用户id", required = true)
                                    @RequestParam String userId){
        return IMOOCJSONResult.ok(centerUserService.queryUserInfo(userId));
    }

}

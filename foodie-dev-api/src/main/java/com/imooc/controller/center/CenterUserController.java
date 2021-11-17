package com.imooc.controller.center;

import com.imooc.controller.BaseController;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制类
 * @Author Mengdexin
 * @date 2021 -11 -16 -22:37
 */
@RestController
@Api(value = "用户数据", tags = {"用户相关接口"})
@RequestMapping(value = "userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterService centerService;

    @PostMapping(value = "")
    @ApiOperation(value = "修改头像", notes = "修改头像", httpMethod = "POST")
    public IMOOCJSONResult file(@ApiParam(value = "userId",name = "用户id", required = true)
                                @RequestParam String userId,
                                MultipartFile file,
                                HttpServletRequest request,
                                HttpServletResponse response){
        if(file == null){
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        String fileSpace = FILE_USER_FACE_URL;
        String uploadPathPrefix = File.separator + userId;
//        String filePath =
        return IMOOCJSONResult.ok();
    }


    @PostMapping(value = "update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    public IMOOCJSONResult update(@ApiParam(value = "userId", name = "用户id", required = true)
                                  @RequestParam String userId,
                                  @RequestBody @Valid CenterUserBO centerUserBO,
                                  BindingResult result,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        if(result.hasErrors()){
            return IMOOCJSONResult.errorMap(getErrorMap(result));
        }
        Users usersResult = centerService.updateUserInfo(userId, centerUserBO);
        //将敏感信息进行清空或者等等
        // TODO 以后会用redis和token等概念
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersResult), true);
        return IMOOCJSONResult.ok();
    }

    public Map<String, Object> getErrorMap(BindingResult result){
        Map<String, Object> map = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()){
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return map;
    }

}

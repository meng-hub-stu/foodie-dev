package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resource.FileUploadResource;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private CenterUserService centerUserService;

    @Autowired
    private FileUploadResource fileUploadResource;

    @PostMapping(value = "uploadFace")
    @ApiOperation(value = "修改头像", notes = "修改头像", httpMethod = "POST")
    public IMOOCJSONResult file(@ApiParam(name = "userId",value = "用户id", required = true)
                                @RequestParam String userId,
                                MultipartFile file,
                                HttpServletRequest request,
                                HttpServletResponse response){
        if(file == null){
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        //保存文件的地址
//        String fileSpace = FILE_USER_FACE_URL;
        String fileSpace = fileUploadResource.getFileUserFaceUrl();
        //前缀文件
        String uploadPathPrefix = File.separator + userId;
        FileOutputStream fileOutputStream = null;
        try{
            if(file != null){
                String fileName = file.getOriginalFilename();
                String fileNameArr[] = fileName.split("\\.");
                String suffix = fileNameArr[fileNameArr.length - 1];
                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg") ) {
                    return IMOOCJSONResult.errorMsg("图片格式不正确！");
                }
                //文件重命名
                String newFileName = "face-" + userId + "." + suffix;
                //文件的完整地址
                String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                //文件存储在数据库的名称
                uploadPathPrefix += "/" + newFileName;
                File outFile = new File(finalFacePath);
                if(outFile.getParentFile() != null){
                    outFile.getParentFile().mkdirs();
                }
                //文件进行保存
                fileOutputStream = new FileOutputStream(outFile);
                InputStream inputStream = file.getInputStream();
                IOUtils.copy(inputStream, fileOutputStream);
            }
        }catch (Exception e){
            e.printStackTrace();
            if(fileOutputStream != null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        String userFaceUrl = fileUploadResource.getFileServerUrl() + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users users = centerUserService.updateUserFace(userId, userFaceUrl);
        //更新redis会话操作
        //重新设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(createUserToken(users)), true);
        return IMOOCJSONResult.ok();
    }


    @PostMapping(value = "update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    public IMOOCJSONResult update(@ApiParam(name = "userId", value = "用户id", required = true)
                                  @RequestParam String userId,
                                  @RequestBody @Valid CenterUserBO centerUserBO,
                                  BindingResult result,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        if(result.hasErrors()){
            return IMOOCJSONResult.errorMap(getErrorMap(result));
        }
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        //将敏感信息进行清空或者等等
        // 以后会用redis和token的会话
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(createUserToken(users)), true);
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

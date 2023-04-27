package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.resource.FileResource;
import com.imooc.service.FsService;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 用户控制类
 * @Author Mengdexin
 * @date 2021 -11 -16 -22:37
 */
@RestController
@Api(value = "用户数据", tags = {"用户相关接口"})
@RequestMapping(value = "fast")
public class CenterUserFaceController extends BaseController{

    @Autowired
    private FsService faService;

    @Autowired
    private CenterUserService centerUserService;

    @Resource
    private FileResource fileResource;

    @PostMapping(value = "uploadFace")
    @ApiOperation(value = "修改头像", notes = "修改头像", httpMethod = "POST")
    public IMOOCJSONResult file(@ApiParam(name = "userId",value = "用户id", required = true)
                                @RequestParam String userId,
                                MultipartFile file,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception{
        String path = null;
        if(file != null){
            String fileName = file.getOriginalFilename();
            String fileNameArr[] = fileName.split("\\.");
            String suffix = fileNameArr[fileNameArr.length - 1];
            if (!suffix.equalsIgnoreCase("png") &&
                    !suffix.equalsIgnoreCase("jpg") &&
                    !suffix.equalsIgnoreCase("jpeg") ) {
                return IMOOCJSONResult.errorMsg("图片格式不正确！");
            }
            path = faService.upload(file, suffix);
            System.out.println(path);
        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        if (isBlank(path)) {
            return IMOOCJSONResult.errorMsg("上传失败");
        }

        String userFaceUrl = fileResource.getHost() + path;
        Users users = centerUserService.updateUserFace(userId, userFaceUrl);
        //更新redis会话操作
        //重新设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(createUserToken(users)), true);
        return IMOOCJSONResult.ok();
    }

}

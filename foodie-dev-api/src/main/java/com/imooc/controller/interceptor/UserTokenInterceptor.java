package com.imooc.controller.interceptor;

import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.StringJoiner;

import static com.imooc.controller.BaseController.REDIS_USER_TOKEN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 用户token的拦截器
 * @Author Mengdexin
 * @date 2021 -12 -18 -21:09
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 拦截请求，在访问controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("启用拦截器");
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        if (isNotBlank(userId) && isNotBlank(userToken)) {
            String redisUserToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
            if (isNotBlank(redisUserToken)) {
                if (!userToken.equals(redisUserToken)) {
                    System.out.println("异地登录。。。");
                    returnErrorResult(response, IMOOCJSONResult.errorMsg("异地登录。。。"));
                    return false;
                }
            } else {
                System.out.println("请重新登录。。。");
                returnErrorResult(response, IMOOCJSONResult.errorMsg("请重新登录。。。"));
                return false;
            }
        } else {
            System.out.println("请登录。。。");
            returnErrorResult(response, IMOOCJSONResult.errorMsg("请登录。。。"));
            return false;
        }
        return true;
    }

    public void returnErrorResult(HttpServletResponse response,
                                  IMOOCJSONResult result){
        ServletOutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            outputStream = response.getOutputStream();
            outputStream.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 请求在访问contorller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求在controller之后，渲染试图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

package com.imooc.controller;

import com.imooc.lock.RedisLockApiBack;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Mengdl
 * @date 2021/11/01
 */
@RestController
@Api(value = "hello测试相关代码", tags = {"hello测试相关的接口!"})
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private RedisOperator redisOperator;

    @RedisLockApiBack(methodName = "test1()", expireTime = 100L)
    @GetMapping(value = "/test1")
    public void test1() {
        redisOperator.set("ke", "v1");
    }

    @RedisLockApiBack
    @GetMapping(value = "/test2")
    public void test2() {
        redisOperator.set("ke2", "v2");
    }

    @GetMapping(value = "/test3")
    public void test3(@RequestParam Long fundId) {
        redisOperator.set("ke2", "v2");
    }


    @GetMapping(value = "/hello")
    public Object test(){
        logger.info("info{}", "info的信息");
        logger.warn("warn{}", "warn的信息");
        return "Hello World";
    }

    @GetMapping(value = "/setSession")
    public Object setSession(HttpServletRequest request,
                             HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "users");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        return "ok";
    }

}

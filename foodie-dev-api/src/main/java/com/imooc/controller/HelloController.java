package com.imooc.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Mengdl
 * @date 2021/11/01
 */
@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(value = "/test")
    public Object test(){
        logger.info("info{}", "info的信息");
        logger.warn("warn{}", "warn的信息");
        return "Hello World";
    }

    @GetMapping(value = "/setsession")
    public Object setSession(HttpServletRequest request,
                             HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "users");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        return "ok";
    }

}

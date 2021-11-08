package com.imooc.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mengdl
 * @date 2021/11/01
 */
@RestController
@Api(value = "测试模块", tags = "测试模块")
public class HelloController {

    @GetMapping(value = "/test")
    public Object test(){
        return "Hello World";
    }

}

package com.imooc.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mengdl
 * @date 2021/11/01
 */
@RestController
public class HelloController {

    @GetMapping(value = "/test")
    public Object test(){
        return "Hello World";
    }

}

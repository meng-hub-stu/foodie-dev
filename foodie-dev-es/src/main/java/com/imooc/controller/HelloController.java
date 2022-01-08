package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Mengdexin
 * @date 2022 -01 -08 -12:00
 */
@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    private String hello(){
        return "hello elasticsearch";
    }

}

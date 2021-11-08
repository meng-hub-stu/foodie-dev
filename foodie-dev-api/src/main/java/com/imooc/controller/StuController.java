package com.imooc.controller;

import com.imooc.StuService;
import com.imooc.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:08
 */
@RestController
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping(value = "/getStu/{id}")
    public Stu getStuInfo(@PathVariable(value = "id") Integer id) {
        return stuService.getStu(id);
    }

}

package com.imooc.controller;

import com.imooc.pojo.bo.OrderBO;
import com.imooc.service.StuService;
import com.imooc.pojo.Stu;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @Author Mengdexin
 * @date 2021 -11 -03 -22:08
 */
@RestController
@Api(value = "测试代码", tags = {"测试相关的接口"})
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping(value = "stu")
    public IMOOCJSONResult test(OrderBO stu){
        System.out.println(stu);
        return IMOOCJSONResult.ok();
    }

    @GetMapping(value = "/getStu/{id}")
    public Stu getStuInfo(@PathVariable(value = "id") Integer id) {
        return stuService.getStu(id);
    }

    @GetMapping(value = "/getStu")
    public Stu test(@RequestParam(value = "id") String id) {
        return stuService.getStu(123);
    }

    @GetMapping(value = "page-list")
    @ApiOperation(value = "分页查询", notes = "页号和页码", httpMethod = "GET")
    public IMOOCJSONResult getPageList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "row", defaultValue = "8") Integer row){
        return IMOOCJSONResult.ok(stuService.getPageList(page,  row));
    }

}

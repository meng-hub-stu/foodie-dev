package com.imooc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.imooc.entity.StuUser;
import com.imooc.entity.query.StuUserQuery;
import com.imooc.service.IStuUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author Mengdl
 * @date 2022/06/08
 */
@RestController
@RequestMapping(value = "user1")
@Api(value = "用户管理", tags = "用户管理")
@AllArgsConstructor
public class UserController {

    private final IStuUserService stuUserService;

    @GetMapping(value = "list")
    @ApiOperation(value = "查询列表数据", notes = "无条件")
    public Object queryUserList(){
        return stuUserService.list();
    }

    @GetMapping(value = "page")
    @ApiOperation(value = "分页查询数据", notes = "条件")
    public IPage<StuUser> getInfoListPage(StuUserQuery query){
        return stuUserService.page(query.getPage(OrderItem.desc("create_time")), query.wrapperQuery());
    }

    @PostMapping(value = "add")
    @ApiOperation(value = "添加信息的数据", notes = "用户信息")
    public boolean add(@RequestBody StuUser stuUser){
        return stuUserService.saveOrUpdate(stuUser);
    }

    @GetMapping(value = "detail")
    @ApiOperation(value = "查看详情", notes = "id")
    public StuUser detail(@RequestParam @Positive long id){
        return stuUserService.getById(id);
    }

}

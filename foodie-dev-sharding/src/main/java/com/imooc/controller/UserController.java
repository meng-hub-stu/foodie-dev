package com.imooc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.imooc.entity.StuUser;
import com.imooc.entity.query.StuUserQuery;
import com.imooc.service.IStuUserService;
import com.imooc.utils.IMOOCJSONResult;
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
    public IMOOCJSONResult queryUserList(){
        return IMOOCJSONResult.ok(stuUserService.list());
    }

    @GetMapping(value = "page")
    @ApiOperation(value = "分页查询数据", notes = "条件")
    public IMOOCJSONResult getInfoListPage(StuUserQuery query){
        IPage<StuUser> result = stuUserService.page(query.getPage(OrderItem.desc("create_time")), query.wrapperQuery());
        return IMOOCJSONResult.ok(result);
    }

    @PostMapping(value = "add")
    @ApiOperation(value = "添加信息的数据", notes = "用户信息")
    public IMOOCJSONResult add(@RequestBody StuUser stuUser){
        return IMOOCJSONResult.ok(stuUserService.saveOrUpdate(stuUser));
    }

    @GetMapping(value = "detail")
    @ApiOperation(value = "查看详情", notes = "id")
    public IMOOCJSONResult detail(@RequestParam @Positive long id){
        return IMOOCJSONResult.ok(stuUserService.getById(id));
    }

}

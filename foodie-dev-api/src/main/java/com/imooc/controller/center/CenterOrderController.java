package com.imooc.controller.center;

import com.imooc.service.center.CenterService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mengdl
 * @date 2021/11/17
 */
@RestController
@RequestMapping(value = "myorders")
@Api(value = "我的订单", tags = "{我的订单相关接口}")
public class CenterOrderController {

    @Autowired
    private CenterService centerService;

    @PostMapping(value = "statusCounts")
    @ApiOperation(value = "订单的状态", notes = "订单的状态", httpMethod = "POST")
    public IMOOCJSONResult statusCounts(@ApiParam(value = "userId", name = "用户id", required = true)
                                        @RequestParam(value = "userId") String userId){
        return IMOOCJSONResult.ok(centerService.queryUserOrdersStatusCounts(userId));
    }

}

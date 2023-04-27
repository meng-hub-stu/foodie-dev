package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemCommentsBO;
import com.imooc.service.center.MyComentsService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人中心评价控制层
 * @Author Mengdexin
 * @date 2021 -11 -21 -10:25
 */
@RestController
@RequestMapping(value = "mycomments")
@Api(value = "评级控制层", tags = "评价控制相关接口")
public class CenterCommentController extends BaseController {

    @Autowired
    private MyComentsService myComentsService;

    @PostMapping(value = "query")
    @ApiOperation(value = "查询个人评价", notes = "查询个人评价", httpMethod = "POST")
    public IMOOCJSONResult query(@ApiParam(name = "userId", value = "用户id")
                                         @RequestParam(value = "userId") String userId,
                                 @ApiParam(name = "page", value = "页码")
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @ApiParam(name = "pageSize", value = "每页条数")
                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return IMOOCJSONResult.ok(myComentsService.queryMyComments(userId, page, pageSize));
    }

    @PostMapping(value = "pending")
    @ApiOperation(value = "商品列表", notes = "商品列表", httpMethod = "POST")
    public IMOOCJSONResult pending(@ApiParam(name = "userId", value = "用户id")
                                       @RequestParam(value = "userId") String userId,
                                   @ApiParam(name = "orderId", value = "商品id")
                                           @RequestParam(value = "orderId") String orderId){
        IMOOCJSONResult imoocjsonResult = checkOrderData(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()){
            return imoocjsonResult;
        }
        Orders myOrders = (Orders)imoocjsonResult.getData();
        if(YesOrNo.YES.type.equals(myOrders.getIsComment())){
            return IMOOCJSONResult.errorMsg("订单已经被评价");
        }
        List<OrderItems> orderItems = myComentsService.queryPendingComment(orderId);
        return IMOOCJSONResult.ok(orderItems);
    }

    @PostMapping(value = "saveList")
    @ApiOperation(value = "商品进行评价", notes = "商品进行评价", httpMethod = "POST")
    public IMOOCJSONResult comment(@ApiParam(name = "userId", value = "用户id")
                                       @RequestParam(value = "userId") String userId,
                                   @ApiParam(name = "orderId", value = "商品id")
                                       @RequestParam(value = "orderId") String orderId,
                                   @RequestBody List<OrderItemCommentsBO> commentsList){
        IMOOCJSONResult imoocjsonResult = checkOrderData(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()){
            return imoocjsonResult;
        }
        Orders myOrders = (Orders)imoocjsonResult.getData();
        if(YesOrNo.YES.type.equals(myOrders.getIsComment())){
            return IMOOCJSONResult.errorMsg("订单已经被评价");
        }
        if(commentsList == null || commentsList.isEmpty() || commentsList.size() == 0){
            return IMOOCJSONResult.errorMsg("评论不能为空");
        }
        boolean flag = myComentsService.saveItemComments(userId, orderId, commentsList);
        return IMOOCJSONResult.ok(flag);
    }

}

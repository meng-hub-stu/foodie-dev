package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.pojo.Orders;
import com.imooc.service.center.CenterUserService;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mengdl
 * @date 2021/11/17
 */
@RestController
@RequestMapping(value = "myorders")
@Api(value = "我的订单", tags = {"我的订单相关接口"})
public class CenterOrderController extends BaseController {

    @PostMapping(value = "statusCounts")
    @ApiOperation(value = "订单的状态", notes = "订单的状态", httpMethod = "POST")
    public IMOOCJSONResult statusCounts(@ApiParam(name = "userId", value = "用户id", required = true)
                                        @RequestParam(value = "userId") String userId){
        return IMOOCJSONResult.ok(myOrdersService.queryUserOrdersStatusCounts(userId));
    }

    @PostMapping(value = "trend")
    @ApiOperation(value = "订单的动向", notes = "订单的动向", httpMethod = "POST")
    public IMOOCJSONResult trend(@ApiParam(name = "userId", value = "用户id", required = true)
                                 @RequestParam(value = "userId") String userId,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return IMOOCJSONResult.ok(myOrdersService.queryMyOrdersTrend(userId, page, pageSize));
    }

    @PostMapping(value = "query")
    @ApiOperation(value = "我的订单", notes = "我的订单", httpMethod = "POST")
    public IMOOCJSONResult query(@ApiParam(name = "userId", value = "用户id", required = true)
                                 @RequestParam(value = "userId") String userId,
                                 @ApiParam(name = "orderStatus", value = "订单的状态", required = true)
                                 @RequestParam(value = "orderStatus") String orderStatus,
                                 @ApiParam(name = "page", value = "页码")
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @ApiParam(name = "pageSize", value = "每页多少条")
                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return IMOOCJSONResult.ok(myOrdersService.queryOrderData(userId, orderStatus, page, pageSize));
    }

    @GetMapping(value = "deliver")
    @ApiOperation(value = "商品进行发货", notes = "商品进行发货", httpMethod = "GET")
    public IMOOCJSONResult deliver(@ApiParam(name = "orderId", value = "订单的id")
                                   @RequestParam(value = "orderId") String orderId){

        return IMOOCJSONResult.ok(myOrdersService.updateOrderStatusByOrderId(orderId, OrderStatusEnum.WAIT_RECEIVE.type));
    }

    @PostMapping(value = "confirmReceive")
    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    public IMOOCJSONResult confirmReceive(@ApiParam(name = "userId", value = "用户id")
                                                      @RequestParam(value = "userId") String userId,
                                          @ApiParam(name = "orderId", value = "订单id")
                                                  @RequestParam(value = "orderId") String orderId){
        IMOOCJSONResult imoocjsonResult = checkOrderData(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()){
            return IMOOCJSONResult.errorMsg("没有订单数据");
        }
        boolean flag = myOrdersService.updateOrderStatusByOrderId(orderId, OrderStatusEnum.SUCCESS.type);
        if(!flag){
            return IMOOCJSONResult.errorMsg("确定收货失败");
        }
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "delete")
    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    public IMOOCJSONResult delete(String userId, String orderId){
        IMOOCJSONResult imoocjsonResult = checkOrderData(userId, orderId);
        if(imoocjsonResult.getStatus() != HttpStatus.OK.value()){
            return IMOOCJSONResult.errorMsg("没有订单数据");
        }
        boolean flag = myOrdersService.deleteOrderByUserIdAndOrderId(userId, orderId);
        if(!flag){
            return IMOOCJSONResult.errorMsg("删除订单失败");
        }
        return IMOOCJSONResult.ok();
    }

}

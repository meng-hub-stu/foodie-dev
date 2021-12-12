package com.imooc.controller;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.pojo.bo.OrderBO;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.pojo.vo.MerchantOrderVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 订单控制层
 * @author Mengdl
 * @date 2021/11/11
 */
@Api(value = "订单管理", tags = {"订单相关接口"})
@RestController
@RequestMapping(value = "orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisOperator redisOperator;


    @PostMapping(value = "create")
    @ApiOperation(value = "创建订单", notes = "订单参数", httpMethod = "POST")
    public IMOOCJSONResult create(@Valid @RequestBody OrderBO orderBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        System.out.println(orderBO);
        //1.创建订单
        String shopCatResult = redisOperator.get(FOODIE_SHOPCART + ":" + orderBO.getUserId());
        if(isBlank(shopCatResult)){
            return IMOOCJSONResult.errorMsg("购物车数据不正确");
        }
        List<ShopcartBO> shopcartList = JsonUtils.jsonToList(shopCatResult, ShopcartBO.class);
        OrderVO orderVO = orderService.createOrder(orderBO, shopcartList);
        String orderId = orderVO.getOrderId();
        MerchantOrderVO merchantOrderVO = orderVO.getMerchantOrderVO();
        merchantOrderVO.setReturnUrl(PAY_RETURN_URL);
        //测试金额为一分钱
        merchantOrderVO.setAmount(1);
        //2.移除购物车的数据
        // 移除redis中的数据
        shopcartList.removeAll(orderVO.getToBeRemovedShopcatedList());
        redisOperator.set(FOODIE_SHOPCART + ":" + orderBO.getUserId(), JsonUtils.objectToJson(shopcartList));
        //将前段的购物车
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "");
        //3.向支付中心发送当前订单，保存支付中心的订单，创建一个未支付的订单
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("imoocUserId", "imooc");
        headers.set("password", "imooc");
        HttpEntity<MerchantOrderVO> entity = new HttpEntity<>(merchantOrderVO, headers);
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAY_MERCHANT_URL, entity, IMOOCJSONResult.class);
        IMOOCJSONResult payResult = responseEntity.getBody();
        if(!payResult.isOK()){
            return IMOOCJSONResult.errorMsg("调用支付中心失败，请联系支付中心的管理员");
        }
        return IMOOCJSONResult.ok(orderId);
    }

    @PostMapping(value = "update")
    @ApiOperation(value = "修改订单", notes = "修改订单", httpMethod = "POST")
    public IMOOCJSONResult update(@RequestBody OrderBO orderBO){
        orderService.updateOrder(orderBO);
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "deleted")
    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    public IMOOCJSONResult deleted(@ApiParam(value = "用户的id", name = "userId", required = true)
                                   @RequestParam(value = "userId") String userId,
                                   @ApiParam(value = "订单的id", name = "orderId", required = true)
                                   @RequestParam(value = "orderId") String orderId){
        orderService.deletedOrder(userId, orderId);
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "notifyMerchantOrderPaid")
    @ApiOperation(value = "支付中心回调的地址", notes = "支付中心回调的地址", httpMethod = "POST")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){
        orderService.notifyMerchantOrderPaid(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping(value = "getPaidOrderInfo")
    @ApiOperation(value = "前端轮询获取订单的支付状态", notes = "前端轮询获取订单的支付状态", httpMethod = "POST")
    public IMOOCJSONResult getPaidOrderInfo(@RequestParam(value = "orderId") String orderId){
        return IMOOCJSONResult.ok(orderService.queryOrderStatus(orderId));
    }

}

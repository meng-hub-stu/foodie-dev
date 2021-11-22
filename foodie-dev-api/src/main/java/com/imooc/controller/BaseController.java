package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @Author Mengdexin
 * @date 2021 -11 -14 -20:57
 */
@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    /**
     * 调用支付中心创建订单
     */
    public static final String PAY_MERCHANT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    /**
     * 支付中心的回调地址，用notapp进行映射的
     */
    public static final String PAY_RETURN_URL = "http://z399vh.natappfree.cc/orders/notifyMerchantOrderPaid";
    /**
     * 人员头像的路径
     */
    public static final String FILE_USER_FACE_URL = File.separator + "workspace"
                                                    + File.separator + "images"
                                                    + File.separator + "fooide"
                                                    + File.separator + "face";

    @Autowired
    public MyOrdersService myOrdersService;

    public IMOOCJSONResult checkOrderData(String userId, String orderId){
        Orders orders = myOrdersService.queryMyOrders(userId, orderId);
        if(orders == null){
            return IMOOCJSONResult.errorMsg("没有订单数据");
        }
        return IMOOCJSONResult.ok(orders);
    }



}

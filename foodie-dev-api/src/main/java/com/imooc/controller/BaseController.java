package com.imooc.controller;

import org.springframework.stereotype.Controller;

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
     * 支付中心的回调地址
     */
    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

}

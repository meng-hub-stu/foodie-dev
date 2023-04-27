package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

/**
 * @Author Mengdexin
 * @date 2021 -11 -14 -20:57
 */
@Controller
public class BaseController {

    @Autowired
    private RedisOperator redisOperator;

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String REDIS_USER_TOKEN = "redis_user_token";
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
    /**
     * 首页轮播的redis的key
     */
    public static final String INDEX_CAROUSEL_REDIS_KEY = "index:carousel:redis:key";

    @Autowired
    public MyOrdersService myOrdersService;

    public IMOOCJSONResult checkOrderData(String userId, String orderId){
        Orders orders = myOrdersService.queryMyOrders(userId, orderId);
        if(orders == null){
            return IMOOCJSONResult.errorMsg("没有订单数据");
        }
        return IMOOCJSONResult.ok(orders);
    }


    public UsersVO createUserToken(Users users){
        String userTokenUuid = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + users.getId(), userTokenUuid);
        //实现用户的redis会话
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserUniqueToken(userTokenUuid);
        return usersVO;
    }



}

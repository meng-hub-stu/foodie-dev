package com.imooc.controller.pay.service;

import com.imooc.controller.pay.entity.wx.PreOrderResult;

/**
 * 支付接口
 * @author Mengdl
 * @date 2021/11/16
 */
public interface PayOrderService {
    /**
     * v2版本
     * 微信native统一下单
     * @param body 订单的描述
     * @param out_trade_no 商品id
     * @param total_fee 订单价格
     * @return 返回结果
     */
    PreOrderResult createWechatPay(String body, String out_trade_no, String total_fee) throws Exception;

    /**
     * v3版本
     * 微信native统一下单
      * @param body 订单的描述
     * @param out_trade_no 商品的id
     * @param total_fee 订单的价格
     */
    void createWechatPayV3(String body, String out_trade_no, String total_fee) throws Exception;

    /**
     * 支付宝统一下单
     * @param out_trade_no 订单id
     * @param total_amount 订单价格
     * @param subject 订单名称
     * @param body 描述
     * @param timeout_express 最晚付款时间
     * @return 返回一个form表单的页面，直接让前端进行渲染
     */
    String createAliPay(String out_trade_no, String total_amount, String subject, String body, String timeout_express);

}

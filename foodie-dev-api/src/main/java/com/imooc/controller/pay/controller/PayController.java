package com.imooc.controller.pay.controller;

import com.imooc.controller.pay.entity.PaymentInfoVO;
import com.imooc.controller.pay.entity.wx.PreOrderResult;
import com.imooc.controller.pay.service.PayOrderService;
import com.imooc.pojo.Orders;
import com.imooc.resource.WechatResource;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 调用支付接口
 * @author Mengdl
 * @date 2021/11/16
 */
@RestController
@RequestMapping(value = "pay")
public class PayController {

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    public RedisOperator redis;
    @Autowired
    private WechatResource wxPayResource;

    @PostMapping(value = "创建订单")
    @ApiOperation(value = "创建订单", notes = "创建订单")
    public IMOOCJSONResult createWechatPay(@RequestBody Map<String, Object> map){
        return IMOOCJSONResult.ok();
    }

    @PostMapping(value = "createWechatPay")
    @ApiOperation(value = "调用微信获取支付二维码", notes = "调用微信获取支付二维码", httpMethod = "POST")
    public IMOOCJSONResult getWechatImage(@RequestParam(value = "merchantUserId") String merchantUserId,
                                           @RequestParam(value = "merchantOrderId") String merchantOrderId) throws Exception {
        //订单号
        String out_trade_no = merchantOrderId;
        //商品价格
        String total_fee = "1";
        //商品的描述
        String body = "天天吃货-付款用户[" + merchantUserId + "]";
        Orders waitPayOrder = new Orders();
        if (waitPayOrder != null) {
            String qrCodeUrl = redis.get(wxPayResource.getQrcodeKey() + ":" + merchantOrderId);
            if (StringUtils.isEmpty(qrCodeUrl)) {
                // 订单总金额，单位为分
//                String total_fee = String.valueOf(waitPayOrder.getAmount());
//				String total_fee = "1";	// 测试用 1分钱

                // 统一下单
//                PreOrderResult preOrderResult = wxOrderService.placeOrder(body, out_trade_no, total_fee);
                PreOrderResult preOrderResult = payOrderService.createWechatPay(body, out_trade_no, total_fee);
                qrCodeUrl = preOrderResult.getCode_url();
            }

            PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
//            paymentInfoVO.setAmount(waitPayOrder.getAmount());
            paymentInfoVO.setMerchantOrderId(merchantOrderId);
            paymentInfoVO.setMerchantUserId(merchantUserId);
            paymentInfoVO.setQrCodeUrl(qrCodeUrl);
            redis.set(wxPayResource.getQrcodeKey() + ":" + merchantOrderId, qrCodeUrl, wxPayResource.getQrcodeExpire());
            return IMOOCJSONResult.ok(paymentInfoVO);
        } else {
            return IMOOCJSONResult.errorMsg("该订单不存在，或已经支付");
        }
//        PreOrderResult preOrderResult = payOrderService.createWechatPay(body, out_trade_no, total_fee);
//        PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
//        paymentInfoVO.setAmount(Integer.valueOf(total_fee));
//        paymentInfoVO.setMerchantOrderId(merchantOrderId);
//        paymentInfoVO.setMerchantUserId(merchantUserId);
//        paymentInfoVO.setQrCodeUrl(preOrderResult.getCode_url());
//        return IMOOCJSONResult.ok(paymentInfoVO);
    }

    @PostMapping(value = "createAliPay")
    @ApiOperation(value = "调用阿里的支付接口获取二维码", notes = "调用阿里的支付接口获取二维码", httpMethod = "POST")
    public IMOOCJSONResult createAliPay(@ApiParam(value = "用户的id", name = "merchantUserId", required = true)
                                        @RequestParam(value = "merchantUserId") String merchantUserId,
                                        @ApiParam(value = "订单的id", name = "merchantOrderId", required = true)
                                        @RequestParam(value = "merchantOrderId") String merchantOrderId){
        // 商户订单号, 商户网站订单系统中唯一订单号, 必填
        String out_trade_no = merchantOrderId;
        // 付款金额, 必填 单位元
//        String total_amount = CurrencyUtils.getFen2YuanWithPoint(waitPayOrder.getAmount());
        String total_amount = "0.01";	// 测试用 1分钱
        // 订单名称, 必填
        String subject = "天天吃货-付款用户[" + merchantUserId + "]";
        // 商品描述, 可空, 目前先用订单名称
        String body = subject;
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1d";
        String result = payOrderService.createAliPay(out_trade_no, total_amount, subject, body, timeout_express);
        return IMOOCJSONResult.ok(result);
    }

}

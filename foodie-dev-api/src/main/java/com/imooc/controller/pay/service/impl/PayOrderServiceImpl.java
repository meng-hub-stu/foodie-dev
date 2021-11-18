package com.imooc.controller.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.imooc.resource.AliPayResource;
import com.imooc.resource.WechatResource;
import com.imooc.controller.pay.entity.wx.PreOrder;
import com.imooc.controller.pay.entity.wx.PreOrderResult;
import com.imooc.controller.pay.service.PayOrderService;
import com.imooc.controller.pay.utils.HttpUtil;
import com.imooc.controller.pay.utils.Sign;
import com.imooc.controller.pay.utils.XmlUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 支付接口
 * @author Mengdl
 * @date 2021/11/16
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private WechatResource wechatResource;

    @Autowired
    private AliPayResource aliPayResource;

    @Override
    public PreOrderResult createWechatPay(String body, String out_trade_no, String total_fee) throws Exception{
        PreOrder o = new PreOrder();
        // 生成随机字符串
        String nonce_str = UUID.randomUUID().toString().trim().replaceAll("-", "");
        o.setAppid(wechatResource.getAppId());
        o.setBody(body);
        o.setMch_id(wechatResource.getMerchantId());
        o.setNotify_url(wechatResource.getNotifyUrl());
        o.setOut_trade_no(out_trade_no);
        // 判断有没有输入订单总金额，没有输入默认1分钱
        if (total_fee != null && !total_fee.equals("")) {
            o.setTotal_fee(Integer.parseInt(total_fee));
        } else {
            o.setTotal_fee(1);
        }
        o.setNonce_str(nonce_str);
        o.setTrade_type(wechatResource.getTradeType());
        o.setSpbill_create_ip(wechatResource.getSpbillCreateIp());
        //将所有的参数生成一个sign签名
        SortedMap<Object, Object> p = new TreeMap<Object, Object>();
        p.put("appid", wechatResource.getAppId());
        p.put("mch_id", wechatResource.getMerchantId());
        p.put("body", body);
        p.put("nonce_str", nonce_str);
        p.put("out_trade_no", out_trade_no);
        p.put("total_fee", total_fee);
        p.put("spbill_create_ip", wechatResource.getSpbillCreateIp());
        p.put("notify_url", wechatResource.getNotifyUrl());
        p.put("trade_type", wechatResource.getTradeType());
        // 获得签名
        String sign = Sign.createSign("utf-8", p, wechatResource.getSecrectKey());
        o.setSign(sign);
        // Object转换为XML
        String xml = XmlUtil.object2Xml(o, PreOrder.class);
        // 统一下单地址
        String url = wechatResource.getPlaceOrderUrl();
        // 调用微信统一下单地址
        String returnXml = HttpUtil.sendPost(url, xml);
        // XML转换为Object
        PreOrderResult preOrderResult = (PreOrderResult) XmlUtil.xml2Object(returnXml, PreOrderResult.class);
        return preOrderResult;
    }

    @Override
    public String createAliPay(String out_trade_no, String total_amount, String subject, String body, String timeout_express) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayResource.getGatewayUrl(),
                aliPayResource.getAppId(),
                aliPayResource.getMerchantPrivateKey(),
                "json",
                aliPayResource.getCharset(),
                aliPayResource.getAlipayPublicKey(),
                aliPayResource.getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayResource.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayResource.getNotifyUrl());

        JSONObject jsonObject = new JSONObject();
        alipayRequest.setBizContent(jsonObject.toString());
        //设置请求参数的内容
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //发送请求
        String alipayForm = null;
        try {
            alipayForm = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return alipayForm;
    }

    @Override
    public void createWechatPayV3(String body, String out_trade_no, String total_fee) throws Exception{
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native");
        // 请求body参数
        String reqdata = "{"
                + "\"time_expire\":\"2018-06-08T10:34:56+08:00\","
                + "\"amount\": {"
                + "\"total\":100,"
                + "\"currency\":\"CNY\""
                + "},";
        StringEntity entity = new StringEntity(reqdata,"utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        //获取http请求

        // 加载商户私钥（privateKey：私钥字符串）
     /*   PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));
        // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),apiV3Key.getBytes("utf-8"));
        // 初始化httpClient
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();

        //发送请求并接收响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
            } else if (statusCode == 204) { //处理成功，无返回Body
                System.out.println("success");
            } else {
                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
        } finally {
            response.close();
        }*/

    }
}

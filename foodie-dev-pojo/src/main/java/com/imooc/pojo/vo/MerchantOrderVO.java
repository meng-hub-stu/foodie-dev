package com.imooc.pojo.vo;

/**
 * @Author Mengdexin
 * @date 2021 -11 -14 -22:14
 */
public class MerchantOrderVO {
    private String merchantOrderId; //订单id
    private String merchantUserId; //用户id
    private Integer amount; //支付金额
    private Integer payMethod; //支付方式
    private String returnUrl; //回调地址

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}

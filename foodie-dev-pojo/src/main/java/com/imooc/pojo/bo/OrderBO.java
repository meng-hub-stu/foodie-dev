package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 用于创建订单的对象
 * @Author Mengdexin
 * @date 2021 -11 -12 -23:30
 */
public class OrderBO {

    @ApiModelProperty(value = "用户的id", name = "userId", example = "001", required = true)
    @NotBlank
    private String userId;

    @ApiModelProperty(value = "商品规格的id", name = "itemSpecids", example = "001", required = true)
    @NotBlank
    private String itemSpecIds;

    @ApiModelProperty(value = "地址id", name = "addressId", example = "001", required = true)
    @NotBlank
    private String addressId;

    @ApiModelProperty(value = "支付方式", name = "payMethod", example = "001", required = true)
    @NotNull
    private Integer payMethod;

    @ApiModelProperty(value = "备注", name = "leftMsg", example = "001", required = true)
    private String leftMsg;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemSpecIds() {
        return itemSpecIds;
    }

    public void setItemSpecIds(String itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

    @Override
    public String toString() {
        return "OrderBO{" +
                "userId='" + userId + '\'' +
                ", itemSpecIds=" + itemSpecIds +
                ", addressId='" + addressId + '\'' +
                ", payMethod=" + payMethod +
                ", leftMsg='" + leftMsg + '\'' +
                '}';
    }
}

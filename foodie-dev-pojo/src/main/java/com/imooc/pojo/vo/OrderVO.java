package com.imooc.pojo.vo;

import com.imooc.pojo.bo.ShopcartBO;
import java.util.List;

/**
 * @Author Mengdexin
 * @date 2021 -11 -14 -22:18
 */
public class OrderVO {
    private String orderId;
    private MerchantOrderVO merchantOrderVO;
    private List<ShopcartBO> toBeRemovedShopcatedList;

    public List<ShopcartBO> getToBeRemovedShopcatedList() {
        return toBeRemovedShopcatedList;
    }

    public void setToBeRemovedShopcatedList(List<ShopcartBO> toBeRemovedShopcatedList) {
        this.toBeRemovedShopcatedList = toBeRemovedShopcatedList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrderVO getMerchantOrderVO() {
        return merchantOrderVO;
    }

    public void setMerchantOrderVO(MerchantOrderVO merchantOrderVO) {
        this.merchantOrderVO = merchantOrderVO;
    }
}

package com.imooc.pojo.vo.center;

import io.swagger.annotations.ApiModelProperty;

/**
 * 个人订单的数量view
 * @author Mengdl
 * @date 2021/11/17
 */
public class OrderStatusCountVO {

    public OrderStatusCountVO(Integer waitPayCounts, Integer waitDeliverCounts, Integer waitReceiveCounts, Integer waitCommentCounts) {
        this.waitPayCounts = waitPayCounts;
        this.waitDeliverCounts = waitDeliverCounts;
        this.waitReceiveCounts = waitReceiveCounts;
        this.waitCommentCounts = waitCommentCounts;
    }

    public OrderStatusCountVO() {

    }

    @ApiModelProperty(value = "待付款数量", name = "waitPayCounts", example = "12", readOnly = true)
    private Integer waitPayCounts;
    @ApiModelProperty(value = "待发货数量", name = "waitDeliverCounts", example = "12", readOnly = true)
    private Integer waitDeliverCounts;
    @ApiModelProperty(value = "待收货数量", name = "waitReceiveCounts", example = "12", readOnly = true)
    private Integer waitReceiveCounts;
    @ApiModelProperty(value = "待评价数量", name = "waitCommentCounts", example = "12", readOnly = true)
    private Integer waitCommentCounts;

    public Integer getWaitPayCounts() {
        return waitPayCounts;
    }

    public void setWaitPayCounts(Integer waitPayCounts) {
        this.waitPayCounts = waitPayCounts;
    }

    public Integer getWaitDeliverCounts() {
        return waitDeliverCounts;
    }

    public void setWaitDeliverCounts(Integer waitDeliverCounts) {
        this.waitDeliverCounts = waitDeliverCounts;
    }

    public Integer getWaitReceiveCounts() {
        return waitReceiveCounts;
    }

    public void setWaitReceiveCounts(Integer waitReceiveCounts) {
        this.waitReceiveCounts = waitReceiveCounts;
    }

    public Integer getWaitCommentCounts() {
        return waitCommentCounts;
    }

    public void setWaitCommentCounts(Integer waitCommentCounts) {
        this.waitCommentCounts = waitCommentCounts;
    }
}

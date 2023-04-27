package com.imooc.pojo.vo;

/**
 * @author Mengdl
 * @date 2021/11/11
 */
public class CommentLevelCountsVO {

    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;
    private Integer totalCounts;

    public CommentLevelCountsVO() {

    }

    public CommentLevelCountsVO(Integer goodCounts, Integer normalCounts, Integer badCounts, Integer totalCounts) {
        this.goodCounts = goodCounts;
        this.normalCounts = normalCounts;
        this.badCounts = badCounts;
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }
}

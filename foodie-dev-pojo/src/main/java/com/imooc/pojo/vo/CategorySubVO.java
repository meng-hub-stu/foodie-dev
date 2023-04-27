package com.imooc.pojo.vo;

/**
 * @Author Mengdexin
 * @date 2021 -11 -09 -23:05
 */
public class CategorySubVO {
    private String  subId;
    private String  subName;
    private String  subType;
    private String  subFatherId;

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(String subFatherId) {
        this.subFatherId = subFatherId;
    }
}

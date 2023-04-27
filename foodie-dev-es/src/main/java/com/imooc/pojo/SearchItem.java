package com.imooc.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Author Mengdexin
 * @date 2022 -01 -02 -21:45
 */

@Document(indexName = "foodie-items-ik", type = "doc", createIndex = false)
public class SearchItem {

    @Id
    @Field(store = true, index = false,type = FieldType.Text)
    private String itemId;

    @Field(store = true, index = true, type = FieldType.Text)
    private String itemName;

    @Field(store = true, index = false, type = FieldType.Text)
    private String imgUrl;

    @Field(store = true, index = false, type = FieldType.Integer)
    private Integer price;

    @Field(store = true, index = false, type = FieldType.Integer)
    private Integer sellCounts;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    @Override
    public String toString() {
        return "SearchItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", sellCounts=" + sellCounts +
                '}';
    }

}

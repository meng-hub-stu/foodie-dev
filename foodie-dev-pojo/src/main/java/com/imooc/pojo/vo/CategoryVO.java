package com.imooc.pojo.vo;

import java.util.List;

/**
 * @Author Mengdexin
 * @date 2021 -11 -09 -23:02
 */
public class CategoryVO {
  private String  id;
  private String  name;
  private String  type;
  private String  fatherId;
  private List<CategorySubVO> subCatList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public List<CategorySubVO> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<CategorySubVO> subCatList) {
        this.subCatList = subCatList;
    }
}

package com.imooc.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @Author Mengdexin
 * @date 2022 -01 -02 -21:45
 */

@Document(indexName = "stu", type = "_doc")
public class Student {

    @Id
    private Long stuId;
    @Field(store = true, index = false)
    private String name;
    @Field(store = true, index = false)
    private Integer age;
    @Field(store = true, index = false)
    private String signDate;
    @Field(store = true, index = true)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", signDate='" + signDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

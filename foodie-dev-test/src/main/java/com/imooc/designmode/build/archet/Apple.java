package com.imooc.designmode.build.archet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 原型模式-复制模式：1 浅复制-八种基本类型的参数，2 深复制-除了基本类型，包含对象和枚举
 * @author Mengdl
 * @date 2022/04/19
 */
@EqualsAndHashCode
@ToString
public class Apple implements Cloneable{

    private String variety;
    private Integer no;

    public Apple cloneApple(){
        Object obj = null;
        try {
            obj = super.clone();
            return (Apple) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public static void main(String[] args) {
        Apple apple = new Apple();
        Apple apple1 = apple.cloneApple();
        apple.setNo(12);
        apple1.setVariety("你好");
        System.out.println(apple);
        System.out.println(apple1);
    }

}

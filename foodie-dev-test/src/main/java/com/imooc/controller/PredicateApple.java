package com.imooc.controller;

import com.google.common.collect.Lists;
import com.imooc.entity.Apple;
import com.imooc.service.AppleFormatter;
import com.imooc.service.ApplePredicate;
import com.imooc.service.impl.AppleFancyFormatter;
import com.imooc.service.impl.AppleGreenColorPredicate;
import com.imooc.service.impl.AppleRedAndHeavyPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 测试自定义函数编程
 * @author Mengdl
 * @date 2022/01/28
 */
public class PredicateApple {
    public static void main(String[] args) {




        /**
         * 自定义函数编程方法
         */
        filter(Lists.newArrayList(), (Apple a) -> "red".equals(a.getColor()));

        /**
         * 使用lambda的方式
         */
        List<Apple> result =
                filterApples(Lists.newArrayList(), (Apple apple) -> "red".equals(apple.getColor()));


        /**
         * 匿名内部类
         */
        filterApples(Lists.newArrayList(), new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });

        /**
         * 根据行为参数进行获取相应的数据
         */
        List<Apple> redAndHeavyApples =
                filterApples(Lists.newArrayList(), new AppleGreenColorPredicate());

        /**
         * 行为实现获取相应的类型
         */
        List<Apple> apples = filterApples(Lists.newArrayList(), new AppleRedAndHeavyPredicate());

        /**
         * 自定义行为实例化
         */
        prettyPrintApple(Lists.newArrayList(), new AppleFancyFormatter());

        /**
         * 最简单的方式
         */
        filterApplesByColor(Lists.newArrayList(), "red");

    }

    /**
     * 使用函数编程的方式
     * @param list
     * @param p
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }



    /**
     * 根据行为参数获取数据
     * @param inventory
     * @param formatter
     */
    public static void prettyPrintApple(List<Apple> inventory,
                                        AppleFormatter formatter){
        for(Apple apple: inventory){
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }

    /**
     * 行为参数进行获取数据
     * @param inventory
     * @param p
     * @return
     */
    public static List<Apple> filterApples(List<Apple> inventory,
                                           ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 最简单的方式
     * @param inventory
     * @param color
     * @return
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory,
                                                  String color) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple: inventory){
            if ( apple.getColor().equals(color) ) {
                result.add(apple);
            }
        }
        return result;
    }

}

package com.imooc.controller;

import com.google.common.base.Function;
import com.google.common.cache.Weigher;
import com.imooc.entity.Apple;

/**
 * @author Mengdl
 * @date 2022/01/28
 */
public class LamabdaThis {
    public static void main(String[] args) {

        String aaa = "123213";

        Function<String, Integer> stringIntegerFunction = (String s) -> s.length();
        Integer apply = stringIntegerFunction.apply(aaa);
        System.out.println(apply);

        Function<Apple, Boolean> appleBooleanFunction = (Apple a) -> a.getWeight() > 150;
        appleBooleanFunction.apply(new Apple("aaa", 180));



        Weigher<Apple, Apple> appleAppleWeigher = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

        appleAppleWeigher.weigh(null, null);


    }


}

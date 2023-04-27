package com.imooc.controller;

import com.google.common.collect.Maps;
import com.imooc.entity.Apple;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Mengdl
 * @date 2022/05/23
 */
public class FunctionThis {

    public static void main(String[] args) {
        Apple red = new Apple("red", 18);
        //通过什么获得什么
        System.out.println(covert().apply(red));
    }

    private static Function<Apple, String> covert() {
        return apple -> apple.getColor();
    }

}

package com.imooc.service.impl;

import com.imooc.entity.Apple;
import com.imooc.service.AppleFormatter;

/**
 * @author Mengdl
 * @date 2022/01/28
 */
public class AppleFancyFormatter implements AppleFormatter {
    @Override
    public String accept(Apple apple) {
        String characteristic = apple.getWeight() > 150 ? "heavy" :
                "light";
        return "A " + characteristic +
                " " + apple.getColor() +" apple";
    }

}

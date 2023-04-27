package com.imooc.service.impl;

import com.imooc.entity.Apple;
import com.imooc.service.AppleFormatter;

/**
 * @author Mengdl
 * @date 2022/01/28
 */
public class AppleSimpleFormatter implements AppleFormatter {

    @Override
    public String accept(Apple apple) {
        return "An apple of " + apple.getWeight() + "g";
    }
}

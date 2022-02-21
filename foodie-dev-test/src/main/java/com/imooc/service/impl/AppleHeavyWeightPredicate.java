package com.imooc.service.impl;

import com.imooc.entity.Apple;
import com.imooc.service.ApplePredicate;

/**
 * @author Mengdl
 * @date 2022/01/28
 */
public class AppleHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple){
        return apple.getWeight() > 150;
    }
}

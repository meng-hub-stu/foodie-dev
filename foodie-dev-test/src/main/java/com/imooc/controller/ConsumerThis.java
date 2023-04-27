package com.imooc.controller;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Mengdl
 * @date 2022/05/19
 */
public class ConsumerThis {

    public static void main(String[] args) {
        Consumer<Mask> brand = m -> m.setBrand("3m");
        Consumer<Mask> type = m -> m.setType("N95");
        Consumer<Mask> price = m -> m.setPrice(19.9);
        Consumer<Mask> print = System.out::println;
        brand.andThen(type)
                .andThen(price)
                .andThen(print)
                .accept(new Mask());
       //传递行为参数，消费者模式
        Mask n95 = sendMask(v -> {
            v.setType("N95");
            v.setBrand("3m");
            v.setPrice(20.1);
            String brand1 = v.getBrand();
            System.out.println(v);
            System.out.println(brand1);
        });
        System.out.println(n95);
        List<String> list = Arrays.asList("jack", "rose", "zhangsan", "wangwu");
        list.forEach(System.out::println);

    }

    private static Mask sendMask(Consumer<Mask> consumer) {
        Mask mask = new Mask();
        consumer.accept(mask);
        return mask;
    }

    @Data
    private static class Mask {
        private String brand;
        private String type;
        private Double price;
    }
}

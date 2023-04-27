package com.imooc.controller;

import java.util.function.Consumer;

/**
 * 执行某块代码前后，想要开启和关闭某些东西时
 * @author Mengdl
 * @date 2022/05/19
 */
public class DemoConsumer {
    public static void main(String[] args) {
        closeIgnoreTenant(()->{
            //业务逻辑代码
            System.out.println("working");
        });
        closeIgnoreTenant( o ->{
            System.out.println("working");
        });
    }

    public static void closeIgnoreTenant(Runnable runnable) {
        System.out.println("open");
        try {
            runnable.run();
        } finally {
            System.out.println("close");
        }
    }

    public static void closeIgnoreTenant(Consumer<String> consumer) {
        System.out.println("open");
        try {
            consumer.accept("aaa");
        } finally {
            System.out.println("close");
        }
    }

}

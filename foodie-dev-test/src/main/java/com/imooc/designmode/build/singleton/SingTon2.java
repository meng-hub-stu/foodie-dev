package com.imooc.designmode.build.singleton;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 懒汉单例模式
 * @author Mengdl
 * @date 2022/04/19
 */
public class SingTon2 {

    private volatile static SingTon2 singTon2 = null;

    private SingTon2() {

    }

    public static SingTon2 build() {
        if (singTon2 == null) {
            synchronized (SingTon2.class) {
                if (singTon2 == null) {
                    singTon2 = new SingTon2();
                }
            }
        }
        return singTon2;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new
                ThreadPoolExecutor(20, 20, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 20; i++) {
            poolExecutor.submit(() ->
                    System.out.println(SingTon2.build()));
        }
    }

}

package com.imooc.designmode.build.singleton;

/**
 * 饿汉单例模式
 * @author Mengdl
 * @date 2022/04/18
 */
public class SingTon1 {

    private SingTon1() {
    }

    public static SingTon1 singTon1 = new SingTon1();

    public static SingTon1 build() {
        return singTon1;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(SingTon1.build());
        }
    }

}
package com.imooc.designmode.build.builder.food;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class ChickenBurger extends Burger{
    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}

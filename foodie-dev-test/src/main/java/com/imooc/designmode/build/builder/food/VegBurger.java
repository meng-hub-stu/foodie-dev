package com.imooc.designmode.build.builder.food;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class VegBurger extends Burger{
    @Override
    public float price() {
        return 25.0f;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }
}

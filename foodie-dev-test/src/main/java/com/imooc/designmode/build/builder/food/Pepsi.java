package com.imooc.designmode.build.builder.food;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class Pepsi extends ColdDrink{
    @Override
    public float price() {
        return 35.0f;
    }

    @Override
    public String name() {
        return "Pepsi";
    }
}

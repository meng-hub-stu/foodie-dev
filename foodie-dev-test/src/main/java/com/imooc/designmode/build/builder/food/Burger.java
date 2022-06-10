package com.imooc.designmode.build.builder.food;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public abstract class Burger implements Item{

    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract  float price();
}

package com.imooc.designmode.struct.bridge;

/**
 * 实体桥接实现类
 * @author Mengdl
 * @date 2022/04/20
 */
public class GreenCircle implements DrawAPI{
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}

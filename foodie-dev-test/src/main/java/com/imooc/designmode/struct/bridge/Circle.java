package com.imooc.designmode.struct.bridge;

/**
 * 创建实现了 Shape 抽象类的实体类。
 * @author Mengdl
 * @date 2022/04/20
 */
public class Circle extends Shape{

    private int x, y, radius;

    public Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(radius,x,y);
    }

}

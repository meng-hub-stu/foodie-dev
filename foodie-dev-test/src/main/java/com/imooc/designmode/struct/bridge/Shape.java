package com.imooc.designmode.struct.bridge;

/**
 * 使用 DrawAPI 接口创建抽象类 Shape。
 * @author Mengdl
 * @date 2022/04/20
 */
public abstract class Shape {

    protected DrawAPI drawAPI;

    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    public abstract void draw();

}

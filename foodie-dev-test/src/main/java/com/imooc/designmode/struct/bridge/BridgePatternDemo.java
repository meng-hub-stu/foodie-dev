package com.imooc.designmode.struct.bridge;

/**
 * 桥连模式测试类
 * @author Mengdl
 * @date 2022/04/20
 */
public class BridgePatternDemo {
    public static void main(String[] args) {
        Shape redCircle = new Circle(100,100, 10, new RedCircle());
        Shape greenCircle = new Circle(100,100, 10, new GreenCircle());
        redCircle.draw();
        greenCircle.draw();
    }

}

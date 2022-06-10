package com.imooc.designmode.build.archet;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *  定义电动车类
 *  组装一个电动车需要手刹蓄电池等部件
 *  要实现深克隆需要允许序列化反序列化操作
 * @author Mengdl
 * @date 2022/04/19
 */
@Data
@ToString
public class Bicycle implements Cloneable, Serializable, ProtoType{

    //电动车品牌
    public String brand;
    //电动车生产编号
    public int no;
    //手刹组件
    public ParkingBrake parking;
    //蓄电池
    public Accumulator accumulator;

    @Override
    public ProtoType getShallowCloneInstance() {
        Object obj=null;
        try {
            obj = super.clone();
            return (Bicycle)obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("不支持克隆");
            return null;
        }
    }

    @Override
    public ProtoType getDeepCloneInstance(ProtoType protoType) {
        //调用工具类中的序列化对象方法复制对象
        return ProtoTypeUtil.getSerializInstance(protoType);
    }


    public static void main(String[] args) {
        Bicycle bicycle = new Bicycle(); //创建电动车原型对象
        ParkingBrake attachment = new ParkingBrake(); //创建手刹对象
        Accumulator accumulator = new Accumulator(); //创建蓄电池对象
        bicycle.setAccumulator(accumulator); //将蓄电池组装到电动车中
        bicycle.setParking(attachment);    //将手刹添加到电动车中
        SteelWire steel = new SteelWire();     //钢丝对象
        attachment.setSteel(steel);    //将钢丝对象组装到手刹中
        Bicycle proto = (Bicycle)bicycle.getDeepCloneInstance(bicycle); //深克隆，复制一个新的电动车对象
//        Bicycle proto = (Bicycle)bicycle.getShallowCloneInstance();  //浅克隆，

        System.out.println("电动车"+(proto==bicycle));
        System.out.println("电动车手刹"+(proto.getParking()==bicycle.getParking()));
        System.out.println("手刹钢丝"+(proto.getParking().getSteel()==bicycle.getParking().getSteel()));
        System.out.println("电动车蓄电池"+(proto.getAccumulator()==bicycle.getAccumulator()));
        //对象实现接口的深度复制
        ProtoType deepCloneInstance = bicycle.getDeepCloneInstance(bicycle);
        System.out.println(deepCloneInstance);
    }



}

package com.imooc.designmode.build.archet;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 电动车组件：手刹
 * @author Mengdl
 * @date 2022/04/19
 */
@Data
@ToString
public class ParkingBrake implements Serializable, ProtoType,Cloneable {

    //手刹品牌
    public String brand;
    //出厂编号
    public int no;
    //钢丝
    public SteelWire steel;

    @Override
    public ProtoType getShallowCloneInstance() {
        Object obj=null;
        try {
            obj = super.clone();
            return (ParkingBrake)obj;
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

}

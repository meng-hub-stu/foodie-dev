package com.imooc.designmode.build.archet;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 电车组件：蓄电池
 * @author Mengdl
 * @date 2022/04/19
 */
@Data
@ToString
public class Accumulator implements Serializable,ProtoType,Cloneable{

    //蓄电池品牌
    public String brand;
    //出厂编号
    public int no;

    @Override
    public ProtoType getShallowCloneInstance() {
        Object obj=null;
        try {
            obj = super.clone();
            return (Accumulator)obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("不支持克隆");
            return null;
        }
    }

    @Override
    public ProtoType getDeepCloneInstance(ProtoType protoType) {
        return ProtoTypeUtil.getSerializInstance(protoType);
    }

}

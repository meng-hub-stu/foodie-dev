package com.imooc.designmode.build.archet;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 手刹组件；钢丝
 * @author Mengdl
 * @date 2022/04/19
 */
@Data
@ToString
public class SteelWire implements Serializable,ProtoType,Cloneable{
    //钢丝品牌
    public String brand;
    //钢丝最大拉力值
    public int no;

    @Override
    public ProtoType getShallowCloneInstance() {
        Object obj = null;
        try {
            obj = super.clone();
            return (SteelWire) obj;
        } catch (CloneNotSupportedException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public ProtoType getDeepCloneInstance(ProtoType protoType) {
        return ProtoTypeUtil.getSerializInstance(protoType);
    }

}

package com.imooc.designmode.build.archet;

/**
 * 定义克隆接口
 * @author Mengdl
 * @date 2022/04/19
 */
public interface ProtoType {
    /**
     * 浅克隆
     * @return 返回对象
     * @throws CloneNotSupportedException
     */
    ProtoType getShallowCloneInstance()throws CloneNotSupportedException;

    /**
     * 深克隆
     * @param protoType 入参
     * @return 返回对象
     */
    ProtoType getDeepCloneInstance(ProtoType protoType);

}

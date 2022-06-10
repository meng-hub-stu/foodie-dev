package com.imooc.designmode.build.archet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 原型模式工具类
 * @author Mengdl
 * @date 2022/04/19
 */
public class ProtoTypeUtil {

    /**
     * 通过序列化方式获取一个深克隆对象
     * 其实就是复制一个全新的对象并且这个对象的引用属性也会单独复制出来
     * 与原对象没有任何关联
     * @param prototype
     * @return
     */
    public static ProtoType getSerializInstance(ProtoType prototype) {
        try {
            //创建输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(prototype);
            //创建输入流
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            ProtoType copy = (ProtoType) ois.readObject();
            bos.flush();
            //关闭输出流
            bos.close();
            //关闭输入流
            ois.close();
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

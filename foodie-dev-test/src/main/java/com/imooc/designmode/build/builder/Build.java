package com.imooc.designmode.build.builder;

/**
 * 建造者接口
 * @author Mengdl
 * @date 2022/04/19
 */
public interface Build {
    /**
     * 赋值
     * @param val
     */
    void makeId(String val);

    /**
     * 赋值
     * @param val
     */
    void makeName(String val);
    /**
     * 赋值
     * @param val
     */
    void makePassword(String val);
    /**
     * 赋值
     * @param val
     */
    void makePhone(String val);
    /**
     * 赋值
     * @return 返回对象
     */
    User makeUser();

}

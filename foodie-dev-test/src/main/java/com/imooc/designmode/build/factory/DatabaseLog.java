package com.imooc.designmode.build.factory;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class DatabaseLog implements Log{
    @Override
    public void writeLog() {
        System.out.println("database日志开始编写");
    }
}

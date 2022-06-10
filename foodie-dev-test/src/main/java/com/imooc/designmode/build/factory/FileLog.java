package com.imooc.designmode.build.factory;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class FileLog implements Log{
    @Override
    public void writeLog() {
        System.out.println("File日志开始编写");
    }
}

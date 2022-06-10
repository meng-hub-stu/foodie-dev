package com.imooc.designmode.build.factory;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class FileLogFactory implements LogFactory{
    @Override
    public Log createLog() {
        return new FileLog();
    }
}

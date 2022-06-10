package com.imooc.designmode.build.factory;

/**
 * @author Mengdl
 * @date 2022/04/19
 */
public class DatabaseLogFactory implements LogFactory{
    @Override
    public Log createLog() {
        return new DatabaseLog();
    }
}

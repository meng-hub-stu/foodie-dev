package com.imooc.shard;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精准分表算法
 * @author Mengdl
 * @date 2022/06/09
 */
public class MyTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
        /**
         * @param tableNames 表
         * @param shardingValue 分片建的值
         * @return 返回执行的数据库
         */
        for (String tableName : tableNames) {
            String value = String.valueOf(shardingValue.getValue() % tableNames.size() + 1);
            if (tableName.endsWith(value)) {
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }

}

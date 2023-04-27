package com.imooc.shard;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精准分库算法
 * @author Mengdl
 * @date 2022/06/09
 */
public class MyDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    /**
     * @param databaseNames 所有的数据库
     * @param shardingValue 分片建的值
     * @return 返回执行的数据库
     */
    @Override
    public String doSharding(Collection<String> databaseNames, PreciseShardingValue<Integer> shardingValue) {
        //获取一下想要存储哪个数据库，可以使用获取token中的数据，进行分库
        for (String databaseName : databaseNames) {
            String value = String.valueOf(shardingValue.getValue() % databaseNames.size() + 1);
            if (databaseName.endsWith(value)) {
                return databaseName;
            }
        }
        throw new IllegalArgumentException();
        //根据登录信息可以执行数据库
//        return Lists.newArrayList(databaseNames).get(0);
    }

}

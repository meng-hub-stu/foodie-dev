package com.imooc.shard;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mengdl
 * @date 2022/06/10
 */
public class MyTableHintShardingAlgorithm implements HintShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> tableNames, HintShardingValue<Integer> hintShardingValue) {
        Collection<String> result = new ArrayList<>();
        for (String tableName : tableNames) {
            for (Integer shardingValue : hintShardingValue.getValues()) {
                if (tableName.endsWith(String.valueOf(shardingValue % tableNames.size() + 1))) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }
}

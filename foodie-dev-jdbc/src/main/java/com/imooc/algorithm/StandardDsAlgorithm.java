package com.imooc.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * Standard策略分库算法实现类
 *
 * @author DuChao
 * @date 2021/9/16 10:16 上午
 */
public class StandardDsAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {

    /**
     * @param availableTargetNames 可用的目标库名称集合(此例中即 ds1、ds2)
     * @param preciseShardingValue 路由键的精准值(= or in)
     * @return 路由导向的目标库名称
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> preciseShardingValue) {
        // 模拟SQL：select * from course where cid = ? or cid in (?,?)
        // 获取逻辑表名称
        String logicTableName = preciseShardingValue.getLogicTableName();
        // 获取分片键字段名
        String cid = preciseShardingValue.getColumnName();
        // 获取分片键值
        Long value = preciseShardingValue.getValue();

        // 根据分片键值,定制分片算法,实现真实库路由（这里简单的取模）
        String dsName = "ds" + (value % 2 + 1);
        if (!availableTargetNames.contains(dsName)) {
            throw new UnsupportedOperationException("路由库" + dsName + "不存在，请检查配置");
        }
        return dsName;
    }

    /**
     * @param availableTargetNames 可用的目标库名称集合(此例中即 ds1、ds2)
     * @param rangeShardingValue   路由键的范围值(between)
     * @return 路由导向的目标库名称集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> rangeShardingValue) {
        // 模拟SQL：select * from course where cid between 1 and 100;
        // 获取上边界值 100
        Long upper = rangeShardingValue.getValueRange().upperEndpoint();
        // 获取下边界值 1
        Long lower = rangeShardingValue.getValueRange().lowerEndpoint();
        // 获取逻辑表名称
        String logicTableName = rangeShardingValue.getLogicTableName();

        // 范围查询直接全库全表路由
        return availableTargetNames;
    }
}

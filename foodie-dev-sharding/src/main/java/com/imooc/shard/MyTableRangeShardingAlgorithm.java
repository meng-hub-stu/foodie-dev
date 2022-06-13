package com.imooc.shard;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author Mengdl
 * @date 2022/06/13
 */
public class MyTableRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> tables, RangeShardingValue<Long> rangeShardingValue) {
        Range<Long> range = rangeShardingValue.getValueRange();
        long startMillisecond = range.lowerEndpoint();
        long endMillisecond = range.upperEndpoint();
        return getMonthBetween(startMillisecond, endMillisecond, tables);
    }

    /**
     * 计算有效的库表名
     */
    public static Collection<String> getMonthBetween(long minTime, long maxTime,
                                                     Collection<String> availableTargetNames) {
        Collection<String> result = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(new Date(minTime));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(new Date(maxTime));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        while (min.before(max)) {
            String yyyyMM = sdf.format(min.getTime());
            availableTargetNames.forEach(availableTargetName -> {
                if (availableTargetName.endsWith(yyyyMM)) {
                    result.add(availableTargetName);
                }
            });
            min.add(Calendar.MONTH, 1);
        }
        return result;
    }

}

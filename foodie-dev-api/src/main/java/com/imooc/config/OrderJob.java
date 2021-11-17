package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 配置定时器
 * @author Mengdl
 * @date 2021/11/15
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * ps 定时器进行全表扫描数据库，不好，以后可以用rabbitmq进行延时任务处理机制。
     */
//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        System.out.println("执行定时任务，执行时间" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        orderService.autoCloseOrder();
    }
}

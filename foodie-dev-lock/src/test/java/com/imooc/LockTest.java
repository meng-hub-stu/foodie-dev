package com.imooc;

import com.imooc.entity.Order;
import com.imooc.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Mengdx
 * @Date 2022/06/14
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class LockTest {

    @Autowired
    private IOrderService orderService;

    @Test
    public void createOrder(){
        boolean result = orderService.createOrder();
        System.out.println("创建订单结果" +  result);
    }

    @Test
    public void createOrderThread(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(()->{
                orderService.createOrder();
            });
        }

    }

    @Test
    public void queryOrder(){
        Order order = orderService.getById(1536622810368782338L);
        System.out.println(String.format("数据：%s", order));
    }

}

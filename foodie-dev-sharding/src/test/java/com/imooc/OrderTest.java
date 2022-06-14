package com.imooc;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.imooc.entity.Order;
import com.imooc.service.IOrderService;
import com.sun.javafx.binding.StringFormatter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.SchemaOutputResolver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mengdl
 * @date 2022/06/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderTest {

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

package com.imooc;

import com.imooc.entity.Order;
import com.imooc.service.IOrderService;
import com.mengdx.utils.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
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
        Order order = new Order();
        order.setAddress("123");
        order.setTotalPrice(new BigDecimal(10));
        boolean result = orderService.createOrderByThread(order, 5);
        System.out.println("创建订单结果" +  result);
    }

    @Test
    public void createOrderThread() throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        CountDownLatch cdl = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(()->{
                try {
                    cyclicBarrier.await();
                    orderService.createOrder("1");
                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();
    }

    @Test
    public void queryOrder(){
        Order order = orderService.getById(1536622810368782338L);
        System.out.println(String.format("数据：%s", order));
    }

    @Test
    public void deleteOrder(){
        boolean result = orderService.removeById(1536622810368782338L);
        System.out.println(String.format("数据：%s", result));
    }

    @Test
    public void getZkLock() throws Exception{
       /* ZkLock zkLock = new ZkLock();
        boolean lock = zkLock.getLock("order");
        if (lock) {
            log.info("执行业务逻辑");
        }*/
    }

}

package com.imooc.utils;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * redis锁
 *
 * @author Mengdl
 * @date 2022/01/18
 */
public class RedisLockUtil {

    private static final String GET_RESULT = "OK";
    private static final String RELEASE_RESULT = "1";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 获取redis锁
     *
     * @param jedis      redis客户端
     * @param lockKey    锁标识 key
     * @param requestId  锁的持有者,加锁的请求
     * @param expireTime 锁过期时间
     * @return
     */
    public static boolean getLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        //SET_IF_NOT_EXIST 当key不存在时 才处理
        //SET_WITH_EXPIRE_TIME 设置过期时间 时间由expireTime决定
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (GET_RESULT.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean releaseLock(Jedis jedis, String lockKey, String requestId) {
        // 方式1
        //        if (jedis.get(lockKey).equals(requestId)) {//校验当前锁的持有人与但概念请求是否相同
        //            执行在这里时，如果锁被其它请求重新获取到了，此时就不该删除了
        //            jedis.del(lockKey);
        //        }

        //方式2
        // eval() 方法会交给redis服务端执行，减少了从服务端再到客户端处理的过程
        //赋值 KEYS[1] = lockKey   ARGV[1] = requestId
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object releaseResult = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_RESULT.equals(releaseResult.toString())) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        //要创建的线程的数量
        CountDownLatch looker = new CountDownLatch(1);
        CountDownLatch latch = new CountDownLatch(10);
        final String key = "lockKey";
        for (int i = 0; i < latch.getCount(); i++) {
            Jedis jedis = new Jedis();
            UUID uuid = UUID.randomUUID();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        looker.await();
                        System.out.println(Thread.currentThread().getName() + "竞争资源，获取锁");
                        boolean getResult = getLock(jedis, key, uuid.toString(), 5000);
                        if (getResult) {
                            System.out.println(Thread.currentThread().getName() + "获取到了锁，处理业务，用时3秒");
                            Thread.sleep(3000);
                            boolean releaseResult = releaseLock(jedis, key, uuid.toString());
                            if (releaseResult) {
                                System.out.println(Thread.currentThread().getName() + "业务处理完毕，释放锁");
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + "竞争资源失败，未获取到锁");
                        }
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        try {
            System.out.println("准备，5秒后开始");
            Thread.sleep(5000);
            looker.countDown(); //发令  let all threads proceed

            latch.await(); // // wait for all to finish
            System.out.println("结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

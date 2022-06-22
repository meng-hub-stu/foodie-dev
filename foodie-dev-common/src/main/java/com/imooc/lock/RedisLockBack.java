package com.imooc.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Mengdl
 * @date 2022/01/18
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisLockBack {

    private final StringRedisTemplate redisTemplate;
    private static final Long expire = 5000L;
    private static final String LOCK = "lock";

    /**
     * 加锁
     * @param remark 描述
     * @param key key
     * @param expire 时间
     * @return
     */
    public boolean lock(String remark, String key, long expire){
        String value = System.currentTimeMillis() + 1000 + "";
        if (redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.MILLISECONDS)) {
            log.info("lock加锁：" + remark + "-" + key);
            return true;
        }
        //获取当前的锁的值(防止业务代码出错，没有解锁的操作，需要进行重新加锁，重新赋值)
        String currentValue = redisTemplate.opsForValue().get(key);
        if (isNotBlank(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //重新赋值，防止死锁
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (value.equals(oldValue)) {
                return true;
            }
        }
        log.error("lock加锁失败");
        return false;
    }

    /**
     * 释放锁
     * @param remark 描述
     * @param key key
     */
    public void unlock(String remark, String key){
        redisTemplate.delete(key);
        log.info("lock释放锁：" + remark + ":" +key);
    }

}

package com.imooc.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 分布式锁
 * <p>
 * 先获取锁, 获取不到则继续等待(指定时间), 失败次数(指定)次后跳出, 消费降级(抛出,系统繁忙稍后再试) 如果没有重试次数,方法返回null 记得捕获NP 当重试次数有, 但是重试间隔时间没写, 默认200ms 间隔
 * </p>
 * @author qyl
 *
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Order(10)
public class RedisLockAspectBack {

    private final HttpServletRequest request;

    private final RedisLockBack redisLockBack;

//    @Pointcut("@annotation(com.imooc.lock.RedisLockAnn)")
//    public void redisLockAnnotation(){
//    }

//    @Pointcut("@annotation(redisLockAnn)")
//    public void redisLockAnnotation(RedisLockAnn redisLockAnn){
//    }

    @Around("@annotation(redisLockApiBack)")
    public Object methodsAnnotationWithRedisLock(final ProceedingJoinPoint point, RedisLockApiBack redisLockApiBack) throws Throwable {
        Object re = null;
        //获取方法名称
        String key = point.getSignature().getName();
        //获取设置的锁时间
        long expireTime = redisLockApiBack.expireTime();
        if (!"".equals(redisLockApiBack.methodName())) {
            key = redisLockApiBack.methodName();
        }
        try {
            //加锁
            if (redisLockBack.lock("", key, expireTime)) {
                //执行方法
                re = point.proceed();
                //释放锁
                redisLockBack.unlock("", key);
            }
        } catch (Exception e) {
            log.error("redis锁" + e.getMessage());
        } finally {
            //最后都要释放锁
            redisLockBack.unlock("", key);
        }
        return  re;
    }

}

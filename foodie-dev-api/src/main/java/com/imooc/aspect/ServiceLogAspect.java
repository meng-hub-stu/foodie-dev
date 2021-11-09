package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * aop日志处理
 * @author Mengdl
 * @date 2021/11/09
 */
@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * aop通知
     * @param point
     * @return
     * @throws Exception
     */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        log.info("=====开始执行类名：{}，方法名：{}======",
                    //类名
                    point.getTarget().getClass(),
                    //方法
                    point.getSignature().getName());
        //开始执行的时间
        long begin = System.currentTimeMillis();
        //执行的目标
        Object result = point.proceed();
        // 记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else {
            log.info("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        }
        return result;
    }

}

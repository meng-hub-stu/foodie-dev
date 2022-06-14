package com.imooc.lock.second;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mengdl
 * @date 2022/01/18
 */
@Aspect
@Component
@Slf4j
@Order(10)
public class RedisLockAspect {
    //锁名称
    private static final String LOCK_NAME = "lockName";
    //锁等待时间
    private static final String lOCK_WAIT = "lockWait";
    //锁自动过期时间
    private static final String AUTO_UNLOCK_TIME = "autoUnlockTime";
    //锁重试次数
    private static final String RETRY_NUM = "retryNum";
    //重试等待时间
    private static final String RETRY_WAIT = "retryWait";

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.imooc.lock.second.RedisLockApi)")
    public void lockAspect() {}

    @Around("lockAspect()")
    public Object lockAroundAction(ProceedingJoinPoint proceeding) throws Throwable {

        // 获取注解中的参数
        Map<String, Object> annotationArgs = this.getAnnotationArgs(proceeding);
        String lockName = (String)annotationArgs.get(LOCK_NAME);
        Assert.notNull(lockName, "分布式,锁名不能为空");
        int retryNum = (int)annotationArgs.get(RETRY_NUM);
        long retryWait = (long)annotationArgs.get(RETRY_WAIT);
        long lockWait = (long)annotationArgs.get(lOCK_WAIT);
        long autoUnlockTime = (long)annotationArgs.get(AUTO_UNLOCK_TIME);

        // 获取锁
        RLock lock = redissonClient.getLock(lockName);
        try {
            boolean res = lock.tryLock(lockWait, autoUnlockTime, TimeUnit.SECONDS);
            if (res) {
                // 执行主逻辑
                return proceeding.proceed();
            } else {
                // 如果重试次数为零, 则不重试
                if (retryNum <= 0) {
                    log.info(String.format("{%s}已经被锁, 不重试", lockName));
                    throw new Exception(String.format("{%s}已经被锁, 不重试", lockName));
                }
                //重试等待时间
                if (retryWait == 0) {
                    retryWait = 200L;
                }
                // 设置失败次数计数器, 当到达指定次数时, 返回失败
                int failCount = 1;
                while (failCount <= retryNum) {
                    // 等待指定时间ms
                    Thread.sleep(retryWait);
                    if (lock.tryLock(lockWait, autoUnlockTime, TimeUnit.SECONDS)) {
                        // 执行主逻辑
                        return proceeding.proceed();
                    } else {
                        log.info(String.format("{%s}已经被锁, 正在重试[ %s/%s ],重试间隔{%s}毫秒", lockName, failCount, retryNum,
                                retryWait));
                        failCount++;
                    }
                }
                throw new Exception("系统繁忙, 请稍等再试");
            }
        } catch (Throwable throwable) {
            log.error(String.format("执行分布式锁发生异常锁名:{%s},异常名称:{%s}", lockName, throwable.getMessage()));
            throw throwable;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取锁参数
     *
     * @param proceeding
     * @return
     */
    private Map<String, Object> getAnnotationArgs(ProceedingJoinPoint proceeding) {
        // if (!(objs[i] instanceof ExtendedServletRequestDataBinder)
        // && !(objs[i] instanceof HttpServletResponseWrapper)) {

        proceeding.getArgs();
        Object[] objs = proceeding.getArgs();
        String[] argNames = ((MethodSignature)proceeding.getSignature()).getParameterNames(); // 参数名

        Class target = proceeding.getTarget().getClass();
        Method[] methods = target.getMethods();
        String methodName = proceeding.getSignature().getName();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Map<String, Object> result = new HashMap<String, Object>();
                RedisLockApi redisLockApi = method.getAnnotation(RedisLockApi.class);
                if (StringUtils.isNotBlank(redisLockApi.lockParameter())) {
                    for (int i = 0; i < objs.length; i++) {
                        if (redisLockApi.lockParameter().equals(argNames[i])) {
                            result.put(LOCK_NAME, redisLockApi.lockPrefix() + objs[i]);
                            break;
                        }

                    }
                } else {
                    result.put(LOCK_NAME, redisLockApi.lockPrefix());
                }
                //使用el表达式
                //result.put(LOCK_NAME, redisLock.lockPrefix() + getRedisLockName(proceeding, redisLock.lockParameter()));

                result.put(lOCK_WAIT, redisLockApi.lockWait());
                result.put(AUTO_UNLOCK_TIME, redisLockApi.autoUnlockTime());
                result.put(RETRY_NUM, redisLockApi.retryNum());
                result.put(RETRY_WAIT, redisLockApi.retryWait());

                return result;
            }
        }
        throw new RuntimeException("异常");
    }

    private static String getRedisLockName(ProceedingJoinPoint pJoinPoint, String key) {
        //使用SpringEL表达式解析注解上的key
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        //获取方法入参
        Object[] parameterValues = pJoinPoint.getArgs();
        //获取方法形参
        MethodSignature signature = (MethodSignature)pJoinPoint.getSignature();
        Method method = signature.getMethod();
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        //获取该方法的参数名称
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        if (parameterNames == null || parameterNames.length == 0) {
            //方法没有入参,直接返回注解上的key
            return key;
        }
        //解析表达式
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        // 给上下文赋值
        for(int i = 0 ; i < parameterNames.length ; i++) {
            evaluationContext.setVariable(parameterNames[i], parameterValues[i]);
        }
        try {
            Object expressionValue = expression.getValue(evaluationContext);
            if (expressionValue != null && !"".equals(expressionValue.toString())) {
                //返回el解析后的key
                return expressionValue.toString();
            }else{
                //使用注解上的key
                return key;
            }
        } catch (Exception e) {
            //解析失败，默认使用注解上的key
            return key;
        }

    }

}

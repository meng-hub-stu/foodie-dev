package com.imooc.lock;

import java.lang.annotation.*;

/**
 * @author Mengdl
 * @date 2022/01/18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLockApiBack {
    /**
     * 方法名称
     * @return
     */
    String methodName() default "";

    /**
     * 过期的时间
     * @return
     */
    long expireTime() default 5000L;

}

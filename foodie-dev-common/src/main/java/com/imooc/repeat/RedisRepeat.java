package com.imooc.repeat;

import com.imooc.repeat.enums.RateLimiterTypeEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交
 * @author Mengdl
 * @date 2022/01/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisRepeat {

    long DEFAULT_MAX_REQUEST = 1;

    /**
     * 限流类型 {@link RateLimiterTypeEnum}，默认通过请求参数限流
     */
    RateLimiterTypeEnum type() default RateLimiterTypeEnum.PARAM;

    /**
     * max 最大请求数, 默认1
     */
    @AliasFor("value") long max() default DEFAULT_MAX_REQUEST;

    /**
     * max 最大请求数, 默认1
     */
    @AliasFor("max") long value() default DEFAULT_MAX_REQUEST;

    /**
     * 超时时长，默认 3 秒
     */
    long timeout() default 3;

    /**
     * 超时时间单位，默认 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}

package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理机制
 * @author Mengdl
 * @date 2022/06/15
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 运行时异常处理
     * @param ex 异常类型
     * @return 返回结果
     */
    @ExceptionHandler(value = ServiceRunTimeException.class)
    public IMOOCJSONResult serviceRunTimeExceptionHandler(ServiceRunTimeException ex){
        return IMOOCJSONResult.errorMsg(ex.getMessage());
    }

}

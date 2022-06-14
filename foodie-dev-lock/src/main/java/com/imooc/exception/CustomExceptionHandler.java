package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 拦截异常处理
 * @Author Mengdx
 * @Date 2022/06/14
 **/
@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(MessageRunTimeException.class)
    public IMOOCJSONResult handlerMessageRuntimeException(MessageRunTimeException ex){
        return IMOOCJSONResult.errorMsg(ex.getMessage());
    }

}

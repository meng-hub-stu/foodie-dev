package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 异常处理机制
 * @Author Mengdexin
 * @date 2021 -11 -19 -22:12
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 进行捕获异常处理机制
     * @param ex
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerExceptionMaxUploadFile(MaxUploadSizeExceededException ex){
        return IMOOCJSONResult.errorMsg("上传的文件不能超过500k");
    }
}

package com.imooc.exception;

/**
 * 自定运行时异常
 * @Author Mengdx
 * @Date 2022/06/14
 **/
public class MessageRunTimeException extends RuntimeException{
    public MessageRunTimeException(){
        super();
    }

    public MessageRunTimeException(String message){
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}

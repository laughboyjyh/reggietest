package com.itjyh.reggie.common;

/**
 * 自定义业务异常类,对应ategoryServiceImpl中产生的异常
 */

public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }

}

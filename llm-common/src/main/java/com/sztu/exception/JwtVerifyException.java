package com.sztu.exception;

public class JwtVerifyException extends BaseException{
    public JwtVerifyException(){}
    public JwtVerifyException(String msg) {
        super(msg);
    }
}

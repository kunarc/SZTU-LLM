package com.sztu.exception;

public class BaseException extends RuntimeException{
    public BaseException() {
    }
    public BaseException(String Message) {
        super(Message);
    }
}

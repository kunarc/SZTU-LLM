package com.sztu.result;

import lombok.Data;

import java.io.Serializable;
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        result.message = "success";
        result.data = null;
        return result;
    }
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.message = "success";
        result.data = data;
        return result;
    }
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.message = message;
        result.data = null;
        return result;
    }
}

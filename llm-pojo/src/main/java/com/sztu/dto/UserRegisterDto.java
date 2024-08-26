package com.sztu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDto implements Serializable {
    private String studentId;
    private String name;
    private String password;
}

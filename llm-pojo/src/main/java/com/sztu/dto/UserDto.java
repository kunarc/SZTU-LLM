package com.sztu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String password;
}

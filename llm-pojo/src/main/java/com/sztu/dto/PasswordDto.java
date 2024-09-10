package com.sztu.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String studentId;
    private String oldPassword;
    private String newPassword;
}

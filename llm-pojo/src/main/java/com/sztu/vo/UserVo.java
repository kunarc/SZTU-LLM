package com.sztu.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String studentId;
    private String name;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String token;
}

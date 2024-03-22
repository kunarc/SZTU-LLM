package com.sztu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_history")
public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @TableField("user_id")
    private Long userId;
    private String name;
    @TableField("create_time")
    private LocalDateTime createTime;
}

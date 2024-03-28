package com.sztu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("chat_details")
public class ChatDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @TableField("chat_history_id")
    private Long chatHistoryId;
    @TableField("user_chat")
    private String userChat;
    @TableField("ai_chat")
    private String aiChat;
    @TableField("create_time")
    private LocalDateTime createTime;
}

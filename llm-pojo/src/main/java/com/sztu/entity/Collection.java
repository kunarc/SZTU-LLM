package com.sztu.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("collection")
public class Collection {
    @TableField("user_id")
    private Long userId;

    @TableField("id")
    private Long Id;

    @TableField("question_id")
    private Long questionId;
}


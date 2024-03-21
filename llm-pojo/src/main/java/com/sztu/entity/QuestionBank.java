package com.sztu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("question_bank")
public class QuestionBank implements Serializable {

    public String name;

    public Integer id;

    public String answer;

    public String description;

    @TableField("create_time")
    public LocalDateTime createTime;

    @TableField("update_time")
    public LocalDateTime updateTime;


}

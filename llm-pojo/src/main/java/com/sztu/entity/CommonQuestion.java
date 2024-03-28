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
@TableName("common_question")
public class CommonQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String description;
    @TableField("create_time")
    private LocalDateTime createTime;
}

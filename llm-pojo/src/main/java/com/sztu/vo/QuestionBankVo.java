package com.sztu.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class QuestionBankVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;

    private String description;

}

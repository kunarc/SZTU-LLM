package com.sztu.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class QuestionBankVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}

package com.sztu.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuestionBankVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;

    private String description;

}

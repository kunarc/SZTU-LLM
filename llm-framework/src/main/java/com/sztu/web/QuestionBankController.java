package com.sztu.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sztu.entity.QuestionBank;
import com.sztu.result.Result;
import com.sztu.service.QuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionBankController {
    @Autowired
    private QuestionBankService questionBankService;

    @PostMapping("/search/{id}")
    public Result<String> SearchQuestion(@PathVariable("id") Integer id){
        log.info("id:{}",id);
        QuestionBank questionBank = questionBankService.getById(id);
        String answer = questionBank.getAnswer();
        log.info("answer:{}",answer);
        //返回结果
        return Result.success(answer);
    }

}

package com.sztu.web;


import com.sztu.entity.QuestionBank;
import com.sztu.result.Result;
import com.sztu.service.QuestionBankService;
import com.sztu.vo.QuestionBankVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionBankController {
    @Autowired
    private QuestionBankService questionBankService;

    /***
     * 根据题号查询题目答案
     * @param id
     * @return
     */
    @PostMapping("/search{id}")
    public Result<String> searchQuestion(@PathVariable("id") Long id){
        log.info("id:{}",id);
        QuestionBank questionBank = questionBankService.getById(id);
        String answer = questionBank.getAnswer();
        log.info("answer:{}",answer);
        //返回结果
        return Result.success(answer);
    }

    /***
     * 查询题库中的题目返回给前端
     * @return
     */
    @GetMapping
    public Result<List<QuestionBankVo>> getQuestions() {
        log.info("查询题库中的题目");
        return null;
    }
}

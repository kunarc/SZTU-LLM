package com.sztu.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sztu.entity.QuestionBank;
import com.sztu.result.Result;
import com.sztu.service.QuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sztu.vo.QuestionBankVo;

import java.util.ArrayList;
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
    @PostMapping("/search/{id}")
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
        List<QuestionBank> questionBankList = questionBankService.list();
        // 使用Stream API进行转换
        List<QuestionBankVo> questionBankVoList = new ArrayList<>();
        for(QuestionBank questionBank : questionBankList) {
            QuestionBankVo questionBankvo = new QuestionBankVo();
            BeanUtils.copyProperties(questionBank,questionBankvo,"answer","creatTime","updateTime");
            questionBankvo.setId(questionBank.getId());
            questionBankvo.setName(questionBank.getName());
            questionBankvo.setDescription(questionBank.getDescription());
            questionBankVoList.add(questionBankvo);
        }
        log.info("questionBankVoList:{}",questionBankVoList);
        return Result.success(questionBankVoList);
    }

}

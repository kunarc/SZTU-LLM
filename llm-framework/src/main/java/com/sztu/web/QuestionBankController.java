package com.sztu.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sztu.entity.QuestionBank;
import com.sztu.result.Result;
import com.sztu.service.QuestionBankService;
import com.sztu.vo.QuestionBankVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param
     * @return
     */
    @PostMapping("/search/{name}")
    public Result<List<QuestionBankVo>> searchQuestion(@PathVariable("name") String name){
        LambdaQueryWrapper<QuestionBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(QuestionBank::getName, name).select(QuestionBank::getId,QuestionBank::getName);
        List<QuestionBank> questionBanks = questionBankService.getBaseMapper().selectList(queryWrapper);
        List<QuestionBankVo> res = new ArrayList<>();
        if (questionBanks == null || questionBanks.isEmpty()) {
            return Result.success(res);
        }

        questionBanks.forEach((q)-> {
            QuestionBankVo questionBankVo = new QuestionBankVo();
            BeanUtils.copyProperties(q, questionBankVo);
            res.add(questionBankVo);
            //log.info("题目：{}", questionBankVo);
        });
        //log.info("题目：{}", res);
        return Result.success(res);
    }
    @GetMapping
    public Result<QuestionBank> getQuestionBank(@RequestParam("id") Long id) {
        return Result.success(questionBankService.getById(id));
    }
}

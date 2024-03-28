package com.sztu.web;

import com.sztu.entity.CommonQuestion;
import com.sztu.result.Result;
import com.sztu.service.CommonQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonQuestionController {
    @Autowired
    private CommonQuestionService commonQuestionService;
    @GetMapping
    public Result<List<CommonQuestion>> getCommonQuestion() {
        List<CommonQuestion> list = commonQuestionService.list();
        return Result.success(list.subList(0, 3));
    }
}

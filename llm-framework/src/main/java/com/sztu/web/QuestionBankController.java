package com.sztu.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sztu.context.BaseContext;
import com.sztu.entity.Collection;
import com.sztu.entity.QuestionBank;
import com.sztu.result.Result;
import com.sztu.service.CollectionService;
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

    @Autowired
    private CollectionService collectionService;
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

    @PutMapping
    public Result<?> CreateNewCollection(@RequestParam("id") Long id) {
        Long userId = BaseContext.getCurrentId();
        Result<Boolean> isNewCollection = isCollected(id);
        if(isNewCollection.getData()){
            return Result.error("您已收藏该题目");
        }
        // 未收藏过该题目，创建新的收藏记录
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setQuestionId(id);
        collectionService.save(collection);
        return Result.success("收藏成功");
    }

    @GetMapping("/{userId}")
    public Result<List<QuestionBank>> getQuestionBankCollection(@PathVariable("userId") Long userId) {
        LambdaQueryWrapper<Collection> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Collection::getUserId, userId);
        List<Collection> questionBankIds = collectionService.list(queryWrapper);
        if (queryWrapper != null) {
            //log.info("userId: " + userId);
            //log.info("questionBankIds:{}", questionBankIds);
            List<Long> idList = new ArrayList<Long>();
            for(Collection t : questionBankIds){
                idList.add(t.getQuestionId());
            }
            List<QuestionBank> questionBanks = questionBankService.getBaseMapper().selectBatchIds(idList);
            return Result.success(questionBanks);
        }
        return Result.error("查询失败");
    }

    @GetMapping("/{questionId}/isCollected")
    public Result<Boolean> isCollected(@PathVariable("questionId") Long questionId){
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<Collection> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Collection::getUserId, userId).eq(Collection::getQuestionId, questionId);
        int count = collectionService.count(queryWrapper);
        if (count > 0) {
            return Result.success(true);
        }
        return Result.success(false);
    }
}

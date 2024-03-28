package com.sztu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.entity.QuestionBank;
import com.sztu.mapper.QuestionBankMapper;
import com.sztu.service.QuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionBankImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {

}

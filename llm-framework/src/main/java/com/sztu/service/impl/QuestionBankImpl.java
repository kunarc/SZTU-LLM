package com.sztu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.entity.QuestionBank;
import com.sztu.mapper.QuestionBankMapper;
import com.sztu.service.QuestionBankService;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {

}

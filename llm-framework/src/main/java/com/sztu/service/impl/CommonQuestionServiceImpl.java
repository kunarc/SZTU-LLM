package com.sztu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sztu.entity.CommonQuestion;

import com.sztu.mapper.CommonQuestionMapper;

import com.sztu.service.CommonQuestionService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CommonQuestionServiceImpl extends ServiceImpl<CommonQuestionMapper, CommonQuestion> implements CommonQuestionService {

}

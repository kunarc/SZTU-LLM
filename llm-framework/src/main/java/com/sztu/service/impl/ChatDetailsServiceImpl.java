package com.sztu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.context.BaseContext;
import com.sztu.dto.ChatDetailsDto;
import com.sztu.entity.Chat;
import com.sztu.entity.ChatDetails;
import com.sztu.mapper.ChatDetailsMapper;
import com.sztu.service.ChatDetailsService;
import com.sztu.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ChatDetailsServiceImpl extends ServiceImpl<ChatDetailsMapper, ChatDetails> implements ChatDetailsService {

    @Autowired
    private ChatDetailsMapper chatDetailsMapper;
    @Autowired
    private ChatService chatService;

    @Override
    public List<Chat> searchChatDetails(ChatDetailsDto chatDetailsDto) {
        List<Long> chatHistoryIds = chatDetailsMapper.criticalSearch(chatDetailsDto);
        Long userId = BaseContext.getCurrentId();
        List<Long> ids = chatService.getChatHistoryId(chatDetailsDto.getContext(), userId);
        if (chatHistoryIds != null && !chatHistoryIds.isEmpty()) {
            chatHistoryIds.addAll(ids);
            return chatService.getBaseMapper().selectBatchIds(chatHistoryIds);
        }
        if(ids != null && !ids.isEmpty()) {
            return chatService.getBaseMapper().selectBatchIds(ids);
        }
        return new ArrayList<>();
    }
}

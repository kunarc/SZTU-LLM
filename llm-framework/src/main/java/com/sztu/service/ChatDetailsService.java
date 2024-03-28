package com.sztu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sztu.dto.ChatDetailsDto;
import com.sztu.entity.Chat;
import com.sztu.entity.ChatDetails;

import java.util.List;

public interface ChatDetailsService extends IService<ChatDetails> {
    List<Chat> searchChatDetails(ChatDetailsDto chatDetailsDto);
}

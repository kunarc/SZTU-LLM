package com.sztu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sztu.entity.Chat;
import com.sztu.result.Result;
import com.sztu.vo.ChatVo;
import java.util.*;
public interface ChatService extends IService<Chat> {
    Result<List<ChatVo>> getChatHistory();
    List<Long> getChatHistoryId(String name, Long userId);
    void saveChat();
}

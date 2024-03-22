package com.sztu.web;

import com.sztu.result.Result;
import com.sztu.service.ChatService;
import com.sztu.vo.ChatVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {
    @Autowired
    private ChatService chatService;
    @GetMapping
    public Result<List<ChatVo>> getChatHistory() {
        return chatService.getChatHistory();
    }
    @PostMapping("/{count}")
    public Result<?> saveChat(@PathVariable Integer count) {
        log.info("新建聊天" + count);
        chatService.saveChat(count);
        return Result.success();
    }
}

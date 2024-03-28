package com.sztu.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sztu.dto.ChatDetailsDto;
import com.sztu.entity.Chat;
import com.sztu.entity.ChatDetails;
import com.sztu.result.Result;
import com.sztu.service.ChatDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RestController
@RequestMapping("chatDetails")
public class ChatDetailsController {
    @Autowired
    private ChatDetailsService chatDetailsService;
    @GetMapping
    public Result<List<ChatDetails>> getChatDetails(Long chatHistoryId) {
        return Result.success(chatDetailsService.getBaseMapper().selectList(new QueryWrapper<ChatDetails>().eq("chat_history_id", chatHistoryId)));
    }
    @PostMapping
    public Result<List<Chat>> searchChatDetails(@RequestBody ChatDetailsDto chatDetailsDto) {
        log.info("对话详情查询{}", chatDetailsDto);
        return Result.success(chatDetailsService.searchChatDetails(chatDetailsDto));
    }
}

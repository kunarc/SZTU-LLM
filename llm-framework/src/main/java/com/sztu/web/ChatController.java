package com.sztu.web;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sztu.context.BaseContext;
import com.sztu.entity.Chat;
import com.sztu.result.Result;
import com.sztu.service.ChatDetailsService;
import com.sztu.service.ChatService;
import com.sztu.vo.ChatVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatDetailsService chatDetailsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String CHAT_KEY = "chat:";
    /***
     * 查询历史聊天
     * @return
     */
    @GetMapping
    public Result<List<ChatVo>> getChatHistory() {
        return chatService.getChatHistory();
    }

    /***
     * 新增聊天
     * @param
     * @return
     */
    @PostMapping
    public Result<?> saveChat() {
        log.info("新建聊天");
        chatService.saveChat();
        cleanCache(CHAT_KEY + BaseContext.getCurrentId());
        return Result.success();
    }
    @PutMapping
    public Result<?> updateChat(@RequestParam("id") Long id, @RequestParam("name") String name) {
        log.info("修改id为：{}的历史聊天的名字为{}", id, name);
        LambdaUpdateWrapper<Chat> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Chat::getId, id).set(Chat::getName, name);
        chatService.update(lambdaUpdateWrapper);
        cleanCache(CHAT_KEY + BaseContext.getCurrentId());
        return Result.success();
    }
    /***
     * 删除聊天
     * @param ids
     * @return
     */
    @DeleteMapping
    @Transactional
    public Result<?> deleteChat(@RequestBody List<Long> ids) {
        log.info("当前删除id为：{}的历史聊天", ids);
        chatService.removeByIds(ids);
        chatDetailsService.removeByIds(ids);
        cleanCache(CHAT_KEY + BaseContext.getCurrentId());
        return Result.success();
    }
    private void cleanCache(String key) {
        redisTemplate.delete(key);
        log.info("清理缓存成功!!!");
    }
}

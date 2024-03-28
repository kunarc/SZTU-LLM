package com.sztu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.context.BaseContext;
import com.sztu.entity.Chat;
import com.sztu.exception.AddChatException;
import com.sztu.mapper.ChatMapper;
import com.sztu.result.Result;
import com.sztu.service.ChatService;
import com.sztu.vo.ChatVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String CHAT_KEY = "chat:";

    //private static final String CHAT_COUNT_KEY = "chat:count:";
    @Override
    public Result<List<ChatVo>> getChatHistory() {
        Long userId = BaseContext.getCurrentId();
        log.info("正在查询用户id为：{}的历史聊天记录", userId);
        @SuppressWarnings("unchecked")
        List<ChatVo> chatVos = (List<ChatVo>) redisTemplate.opsForValue().get(CHAT_KEY + userId);
        if (chatVos != null && !chatVos.isEmpty()) {
            log.info("缓存中已有该聊天的数据");
//            chatVos = chatVos.stream().sorted((o1, o2) -> {
//                return o2.getCreateTime().compareTo(o1.getCreateTime());
//            }).collect(Collectors.toList());
            return Result.success(chatVos);
        }
        chatVos = new ArrayList<>();
        LambdaQueryWrapper<Chat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chat::getUserId, userId).orderByDesc(Chat::getCreateTime);
        List<Chat> chats = chatMapper.selectList(queryWrapper);
        if (chats != null && !chats.isEmpty()) {
            for (Chat chat : chats) {
                ChatVo chatVo = new ChatVo();
                BeanUtils.copyProperties(chat, chatVo);
                chatVos.add(chatVo);
            }
            // 缓存
            log.info("开始缓存历史聊天");
            redisTemplate.opsForValue().set(CHAT_KEY + userId, chatVos);
            //redisTemplate.opsForValue().set(CHAT_COUNT_KEY + userId, chatVos.size());
        }
        return Result.success(chatVos);
    }

    @Override
    public List<Long> getChatHistoryId(String name, Long userId) {
        return chatMapper.getIdsByName(name, userId);
    }

    @Override
    public void saveChat() {
        Long userId = BaseContext.getCurrentId();
        Chat chat = new Chat(null, userId, "新建聊天", LocalDateTime.now());
        if (this.save(chat)) {
            log.info("新增聊天成功: {}", chat);
        }
        else {
            throw new AddChatException("新增聊天失败");
        }
    }
}

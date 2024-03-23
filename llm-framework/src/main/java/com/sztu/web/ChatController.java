package com.sztu.web;

import com.sztu.result.Result;
import com.sztu.service.ChatService;
import com.sztu.vo.ChatVo;
import lombok.Data;
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
     * @param count
     * @return
     */
    @PostMapping("/{count}")
    public Result<?> saveChat(@PathVariable Integer count) {
        log.info("新建聊天" + count);
        chatService.saveChat(count);
        return Result.success();
    }
    @PutMapping
    public Result<?> updateChat(@RequestParam("id") Long id, @RequestParam("name") String name) {
        log.info("修改id为：{}的历史聊天的名字为{}", id, name);
        return Result.success();
    }
    /***
     * 删除聊天
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<?> deleteChat(@RequestParam("id") Long id) {
        log.info("当前删除id为：{}的历史聊天", id);
        return Result.success();
    }

}

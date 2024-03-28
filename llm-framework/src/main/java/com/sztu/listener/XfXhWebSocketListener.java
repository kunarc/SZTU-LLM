package com.sztu.listener;

import com.alibaba.fastjson.JSONObject;

import com.sztu.context.SpringBeanContext;
import com.sztu.dto.MsgDto;
import com.sztu.dto.ResponseDto;

import com.sztu.entity.ChatDetails;
import com.sztu.exception.ConnectionException;
import com.sztu.service.ChatDetailsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author kunar
 */
@Slf4j
public class XfXhWebSocketListener extends WebSocketListener {
    private Session session;
    private ChatDetails chatDetails;
    private StringBuilder answer = new StringBuilder();
    private static ChatDetailsService chatDetailsService = SpringBeanContext.getBean(ChatDetailsService.class);
    public void setSession(Session session) {
        this.session = session;
    }
    public void setChatDetails(ChatDetails chatDetails) {
        this.chatDetails = chatDetails;
    }
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        // 将大模型回复的 JSON 文本转为 ResponseDTO 对象
        ResponseDto responseData = JSONObject.parseObject(text, ResponseDto.class);
        // 如果响应数据中的 header 的 code 值不为 0，则表示响应错误
        if (responseData.getHeader().getCode() != 0) {
            // 日志记录
            log.error("发生错误，错误码为：" + responseData.getHeader().getCode() + "; " + "信息：" + responseData.getHeader().getMessage());
            return;
        }

        // 将回答进行发送
        for (MsgDto msgDto : responseData.getPayload().getChoices().getText()) {
            try {
                session.getBasicRemote().sendText(msgDto.getContent());
                answer.append(msgDto.getContent());
                log.info("大模型回答信息：{}: 时间：{}",answer.toString(), LocalDateTime.now());
            } catch (IOException e) {
                log.error("发生错误, 请联系管理员");
                throw new RuntimeException(e);
            }
        }
        // 对最后一个文本结果进行处理
        if (2 == responseData.getHeader().getStatus()) {
            try {
                session.getBasicRemote().sendText("DONE");
            } catch (IOException e) {
                log.error("发生错误, 请联系管理员");
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
     /*   try {
            session.getBasicRemote().sendText("系统内部错误，请联系管理员");
        } catch (IOException e) {
            throw new ConnectionException("系统内部错误，请联系管理员");
        }*/
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.info("与大模型断开连接！！！！！！！！");
        chatDetails.setAiChat(answer.toString());
        chatDetails.setCreateTime(LocalDateTime.now());
        chatDetailsService.save(chatDetails);
        super.onClosed(webSocket, code, reason);
    }
}


package com.sztu.websocket;

import com.alibaba.fastjson.JSONArray;
import com.sztu.config.WebSocketConfig;
import com.sztu.context.SpringBeanContext;
import com.sztu.dto.MsgDto;
import com.sztu.entity.ChatDetails;
import com.sztu.listener.XfXhWebSocketListener;
import com.sztu.model.XfXhStreamClient;
import com.sztu.properties.XfXhProperties;
import com.sztu.service.ChatDetailsService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{studentId}/{chatHistoryId}", configurator = WebSocketConfig.class )
public class MyWebSocket {

    private XfXhStreamClient xfXhStreamClient;



    private  XfXhProperties xfXhProperties;

    private ChatDetailsService chatDetailsService;

    /** 当前在线客户端数量（线程安全的） */
    private static AtomicInteger onlineClientNumber = new AtomicInteger(0);

    /** 当前在线客户端集合（线程安全的）：以键值对方式存储，key是连接的编号，value是连接的对象 */
    private static Map<String , Session> onlineClientMap = new ConcurrentHashMap<>();



    /**
     * 客户端与服务端连接成功
     * @param session
     * @param
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("studentId") String studentId, @PathParam("chatHistoryId") Long chatHistoryId){
        /*
            do something for onOpen
            与当前客户端连接成功时
         */
        xfXhStreamClient = SpringBeanContext.getBean(XfXhStreamClient.class);
        xfXhProperties = SpringBeanContext.getBean(XfXhProperties.class);
        chatDetailsService = SpringBeanContext.getBean(ChatDetailsService.class);
        onlineClientNumber.incrementAndGet();
        onlineClientMap.put(session.getId(),session);
        log.info("与客户端连接成功");
    }

    /**
     * 客户端与服务端连接关闭
     * @param session
     * @param
     */
    @OnClose
    public void onClose(Session session, @PathParam("studentId") String studentId){
        /*
            do something for onClose
            与当前客户端连接关闭时
         */
        log.info("与客户端用户：{}断联", studentId);
        onlineClientNumber.decrementAndGet();
        onlineClientMap.remove(session.getId());


    }

    /**
     * 客户端与服务端连接异常
     * @param error
     * @param session
     * @param
     */
    @OnError
    public void onError(Throwable error,Session session, @PathParam("studentId") String studentId) {
        log.error("与客户端连接失败");
    }

    /**
     * 客户端向服务端发送消息
     * @param
     * @param
     * @throws IOException
     */
    @OnMessage
    public void onMsg(Session session,String questions, @PathParam("studentId") String studentId, @PathParam("chatHistoryId") Long chatHistoryId) throws IOException, InterruptedException {
        /*
            do something for onMessage
            收到来自当前客户端的消息时
         */
        log.info("收到来自用户：{}的消息：{}",studentId, questions);

        List<MsgDto> msgDtoList = JSONArray.parseArray(questions, MsgDto.class);
        log.info("当前的消息为{}", msgDtoList);
        // 获取连接令牌
        if (!xfXhStreamClient.operateToken(XfXhStreamClient.GET_TOKEN_STATUS)) {
            //return "当前大模型连接数过多，请稍后再试";
            session.getBasicRemote().sendText("当前大模型连接数过多，请稍后再试");
        }

        // 创建监听器
        XfXhWebSocketListener listener = new XfXhWebSocketListener();
        listener.setChatDetails(ChatDetails.builder().chatHistoryId(chatHistoryId).userChat(msgDtoList.get(msgDtoList.size() - 1).getContent()).build());
        listener.setSession(session);
        // 发送问题给大模型，生成 websocket 连接
        WebSocket webSocket = xfXhStreamClient.sendMsg(UUID.randomUUID().toString().substring(0, 10), msgDtoList, listener);
        if (webSocket == null) {
            // 归还令牌
            xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
            log.error("不能与大模型建立websocket连接");
            //return "系统内部错误，请联系管理员";
            session.getBasicRemote().sendText("系统内部错误，请联系管理员");
        }
        webSocket.close(1000, "");
        xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
    }
}


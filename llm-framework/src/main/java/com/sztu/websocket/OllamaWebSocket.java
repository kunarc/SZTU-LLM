package com.sztu.websocket;

import com.sztu.context.SpringBeanContext;

import com.sztu.model.Ollama4JClient;
import com.sztu.service.ChatDetailsService;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import lombok.extern.slf4j.Slf4j;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
//@Component
//@ServerEndpoint(value = "/websocket/{studentId}/{chatHistoryId}", configurator = WebSocketConfig.class )
public class OllamaWebSocket {
    private ChatDetailsService chatDetailsService;

    /** 当前在线客户端数量（线程安全的） */
    private static AtomicInteger onlineClientNumber = new AtomicInteger(0);

    /** 当前在线客户端集合（线程安全的）：以键值对方式存储，key是连接的编号，value是连接的对象 */
    private static Map<String , Session> onlineClientMap = new ConcurrentHashMap<>();
    private Ollama4JClient ollama4JClient = new Ollama4JClient();


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
    public void onMsg(Session session,String questions, @PathParam("studentId") String studentId, @PathParam("chatHistoryId") Long chatHistoryId) throws IOException, InterruptedException, OllamaBaseException {
        /*
            do something for onMessage
            收到来自当前客户端的消息时
         */
        log.info("收到来自用户：{}的消息：{}",studentId, questions);

        // 创建监听器
        ollama4JClient.callOllamaApi("http://10.108.5.10:11434/", questions, session);
        session.getBasicRemote().sendText("DONE");
    }
}

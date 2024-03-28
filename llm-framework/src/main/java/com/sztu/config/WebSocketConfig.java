package com.sztu.config;

import com.sztu.context.BaseContext;
import com.sztu.context.SpringBeanContext;
import com.sztu.entity.User;
import com.sztu.exception.JwtVerifyException;
import com.sztu.properties.JwtProperties;
import com.sztu.utils.JwtUtil;
import com.sztu.web.UserController;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;


@EnableWebSocket
@Configuration
@Slf4j
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
        //获取请求头
        List<String> list = request.getHeaders().get("Sec-WebSocket-Protocol");


        //当Sec-WebSocket-Protocol请求头不为空时,需要返回给前端相同的响应
        response.getHeaders().put("Sec-WebSocket-Protocol", list);

        /**
         *获取请求头后的逻辑处理
         */
        String token = list.get(0);

        if (token == null) {
            log.info("token为空");
            throw new JwtVerifyException("token为空");
        }

        Claims claims = JwtUtil.parseJWT("kunar", token);
        super.modifyHandshake(sec, request, response);
    }
}

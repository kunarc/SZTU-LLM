package com.sztu.interceptor;

import com.sztu.properties.JwtProperties;
import com.sztu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class JwtVerifyInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始进行拦截");
        // 如果不是当前处理器不是HandlerMethod, 则直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 从request的header去出token
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        if (token == null) {
            log.info("token为空");
            response.setStatus(401);
            return false;
        }
        // 解析token
        // 等下把try catch去掉试试， 看看全局异常处理器是否能够生效。
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long id = Long.valueOf(claims.get("USER_ID").toString());
            log.info("用户id: {}", id);
            return true;
        } catch (Exception ex) {
            log.info("出现异常：{}", ex);
            response.setStatus(401);
            return false;
        }
    }
}

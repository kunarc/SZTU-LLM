package com.sztu.config;

import com.sztu.interceptor.JwtVerifyInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j

public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private JwtVerifyInterceptor jwtVerifyInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtVerifyInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/websocket");
    }
}

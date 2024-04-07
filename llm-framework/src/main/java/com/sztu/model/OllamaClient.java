package com.sztu.model;


import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
;
import java.io.IOException;

import java.util.concurrent.TimeUnit;



@Slf4j
public class OllamaClient {
//    private final OkHttpClient client = new OkHttpClient.Builder() //来屏蔽系统代理
//            .connectTimeout(6000, TimeUnit.SECONDS)//连接超时
//            .writeTimeout(6000, TimeUnit.SECONDS)//写入超时
//            .readTimeout(6000, TimeUnit.SECONDS)//读取超时
//            .build();


    public void callOllamaApi(String url) {
//        client.dispatcher().setMaxRequestsPerHost(2000);
//        client.dispatcher().setMaxRequests(2000);
        OkHttpClient client = new OkHttpClient.Builder() //来屏蔽系统代理
                .connectTimeout(6000, TimeUnit.SECONDS)//连接超时
                .writeTimeout(6000, TimeUnit.SECONDS)//写入超时
                .readTimeout(6000, TimeUnit.SECONDS)//读取超时
                .build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("model", "llama2:13b");
        json.put("prompt", "Why is the sky blue?");
        json.put("stream", true);
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
        Request request = new Request.Builder().url(url).post(requestBody).build();
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                String responseData = response.body().string();
//                System.out.println("Response Data:" + responseData);
//            } else {
//                System.out.println("Error Code:" + response.code());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        EventSource.Factory factory = EventSources.createFactory(client);
        // 自定义监听器
        EventSourceListener eventSourceListener = new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                log.info("ListenerOnOpen============={}", "建立sse连接");
            }

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                //   接受消息 data
                System.out.println(1111111);
                System.out.println(data);
                //super.onEvent(eventSource, id, type, data);


            }

            @Override
            public void onClosed(EventSource eventSource) {
                log.info("ListenerOnClose========== {}", "连接sse关闭");
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                System.out.println(2222);
                //super.onFailure(eventSource, t, response);
            }
        };

        // 创建事件
        factory.newEventSource(request, eventSourceListener);
        //eventSource.connect(client);
        //创建事件
        //factory.newEventSource(request, eventSourceListener);
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                String responseBody = response.body().string();
//                // 处理响应
//                System.out.println(responseBody);
//            } else {
//                System.out.println("Failed to call Ollama API: " + response);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
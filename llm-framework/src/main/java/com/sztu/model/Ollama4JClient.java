package com.sztu.model;


import com.alibaba.fastjson.JSONArray;
import com.sztu.dto.MessageDto;
import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.OllamaStreamHandler;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.models.chat.*;
import io.github.amithkoujalgi.ollama4j.core.types.OllamaModelType;


import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ollama4JClient {
    public void callOllamaApi(String url, String questions, Session session) throws OllamaBaseException, IOException, InterruptedException {
        List<MessageDto> messageDtoList = JSONArray.parseArray(questions, MessageDto.class);
        List<OllamaChatMessage> ollamaChatMessageList = new ArrayList<>();
        int size = messageDtoList.size();
        String content = "";
        for (MessageDto messageDto : messageDtoList) {
            if (size == 1) {
                content = messageDto.getContent();
                break;
            }
            if (messageDto.getRole().equals("system")) {
                ollamaChatMessageList.add(new OllamaChatMessage(OllamaChatMessageRole.SYSTEM, messageDto.getContent(), messageDto.getImages()));
            }
            else if(messageDto.getRole().equals("user")) {
                ollamaChatMessageList.add(new OllamaChatMessage(OllamaChatMessageRole.USER, messageDto.getContent(), messageDto.getImages()));
            }
            else {
                ollamaChatMessageList.add(new OllamaChatMessage(OllamaChatMessageRole.ASSISTANT, messageDto.getContent(), messageDto.getImages()));
            }
            size--;
        }
        OllamaAPI ollamaAPI = new OllamaAPI(url);
        ollamaAPI.setRequestTimeoutSeconds(10);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.LLAMA2_CHINESE + ":7b");
        OllamaChatRequestModel requestModel = builder.withMessages(ollamaChatMessageList).withMessage(OllamaChatMessageRole.USER,content)
                .build();
        OllamaStreamHandler streamHandler = new OllamaStreamHandler() {
            int preLength = 0;
            @Override
            public void accept(String s) {
                try {
                    session.getBasicRemote().sendText(s.substring(preLength));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                preLength = s.length();
            }
        };
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel,streamHandler);

        System.out.println(chatResult.getResponse());
    }
}

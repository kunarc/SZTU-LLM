package com.sztu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public class ChatDetailsDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Long> chatHistoryIds;
    private String context;

    public List<Long> getChatHistoryIds() {
        return chatHistoryIds;
    }

    public void setChatHistoryIds(List<Long> chatHistoryIds) {
        this.chatHistoryIds = chatHistoryIds;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

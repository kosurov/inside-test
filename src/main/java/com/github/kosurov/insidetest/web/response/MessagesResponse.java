package com.github.kosurov.insidetest.web.response;

import java.util.List;

public class MessagesResponse {
    private List<MessageResponse> messages;

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessagesResponse{" +
                "messages=" + messages +
                '}';
    }
}

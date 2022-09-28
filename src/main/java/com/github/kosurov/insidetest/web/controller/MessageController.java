package com.github.kosurov.insidetest.web.controller;

import com.github.kosurov.insidetest.dto.MessageDto;
import com.github.kosurov.insidetest.mapper.MessageMapper;
import com.github.kosurov.insidetest.service.MessageService;
import com.github.kosurov.insidetest.web.request.MessageRequest;
import com.github.kosurov.insidetest.web.response.MessagesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @PostMapping()
    public MessagesResponse saveOrGetMessage(@RequestBody MessageRequest messageRequest) {
        log.info("Message request");
        MessageDto messageDto = messageMapper.messageRequestToMessageDto(messageRequest);
        log.info("Mapped message dto: {}", messageDto);
        String message = messageDto.getMessage();
        MessagesResponse messagesResponse = new MessagesResponse();
        if (message !=null && message.startsWith("history")) {
            String[] messageParts = message.split(" ");
            int messageCount = Integer.parseInt(messageParts[1]);
            log.info("Requested message history: last {} messages", messageCount);
            messagesResponse.setMessages(messageService.getMessages(messageDto.getName(), messageCount)
                    .stream()
                    .map(messageMapper::messageDtoToMessageResponse)
                    .peek(messageResponse -> log.info("Mapped message response: {}", messageResponse))
                    .toList());
        } else {
            log.info("Requested saving new message in database");
            MessageDto createdMessage = messageService.save(messageDto);
            messagesResponse.setMessages(List.of(messageMapper.messageDtoToMessageResponse(createdMessage)));
        }
        log.info("Mapped messages response: {}", messageRequest);
        return messagesResponse;
    }
}

package com.github.kosurov.insidetest.service;

import com.github.kosurov.insidetest.dto.MessageDto;
import com.github.kosurov.insidetest.mapper.MessageMapper;
import com.github.kosurov.insidetest.model.Message;
import com.github.kosurov.insidetest.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Transactional
    public MessageDto save(MessageDto messageDto) {
        Message message = messageMapper.messageDtoToMessage(messageDto);
        log.info("Mapped message: {}", message);
        messageRepository.save(message);
        log.info("Saved message: {}", message);
        return messageMapper.messageToMessageDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> getMessages(String name, int messageCount) {
        List<Message> messages = messageRepository.findAllByName(name, PageRequest.of(0, messageCount,
                Sort.by(Sort.Direction.DESC, "id"))).getContent();
        log.info("Got messages from database: {}", messages);
        return messages.stream()
                .filter(Objects::nonNull)
                .map(messageMapper::messageToMessageDto)
                .peek(messageDto -> log.info("Mapped message dto: {}", messageDto))
                .toList();
    }
}

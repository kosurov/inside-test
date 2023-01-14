package com.github.kosurov.insidetest.mapper;

import com.github.kosurov.insidetest.dto.MessageDto;
import com.github.kosurov.insidetest.model.Message;
import com.github.kosurov.insidetest.web.request.MessageRequest;
import com.github.kosurov.insidetest.web.response.MessageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto messageRequestToMessageDto(MessageRequest messageRequest);

    MessageResponse messageDtoToMessageResponse(MessageDto messageDto);

    MessageDto messageToMessageDto(Message message);

    Message messageDtoToMessage(MessageDto messageDto);
}

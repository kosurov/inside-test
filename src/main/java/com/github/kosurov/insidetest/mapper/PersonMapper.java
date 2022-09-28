package com.github.kosurov.insidetest.mapper;

import com.github.kosurov.insidetest.dto.AuthDto;
import com.github.kosurov.insidetest.model.Person;
import com.github.kosurov.insidetest.web.request.PersonRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    AuthDto authRequestToAuthDto(PersonRequest personRequest);

    PersonRequest authDtoToAuthRequest(AuthDto authDto);

    AuthDto personToAuthDto(Person person);

    Person authDtoToPerson(AuthDto authDto);

    Person authRequestToPerson(PersonRequest personRequest);
}

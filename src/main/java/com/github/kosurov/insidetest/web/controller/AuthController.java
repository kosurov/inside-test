package com.github.kosurov.insidetest.web.controller;

import com.github.kosurov.insidetest.dto.AuthDto;
import com.github.kosurov.insidetest.exception.IncorrectCredentialsException;
import com.github.kosurov.insidetest.exception.PersonAlreadyExistsException;
import com.github.kosurov.insidetest.mapper.PersonMapper;
import com.github.kosurov.insidetest.model.Person;
import com.github.kosurov.insidetest.security.JwtUtil;
import com.github.kosurov.insidetest.service.RegistrationService;
import com.github.kosurov.insidetest.validation.PersonValidator;
import com.github.kosurov.insidetest.web.request.PersonRequest;
import com.github.kosurov.insidetest.web.response.JwtTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final PersonMapper personMapper;
    private final PersonValidator personValidator;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, RegistrationService registrationService, PersonMapper personMapper, PersonValidator personValidator) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.registrationService = registrationService;
        this.personMapper = personMapper;
        this.personValidator = personValidator;
    }

    @PostMapping("/registration")
    public JwtTokenResponse performRegistration(@RequestBody PersonRequest personRequest,
                                                   BindingResult bindingResult) {
        log.info("Registration request");
        Person person = personMapper.authRequestToPerson(personRequest);
        log.info("Mapped person: {}", person);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Person validation failed");
            throw new PersonAlreadyExistsException("Person already exists");
        }
        log.info("Person validation OK");
        registrationService.register(person);
        log.info("Registered person: {}", person);
        String token = jwtUtil.generateToken(person.getName());
        log.info("Token successfully generated");
        return new JwtTokenResponse(token);
    }

    @PostMapping("/login")
    public JwtTokenResponse performLogin(@RequestBody PersonRequest personRequest) {
        log.info("Authentication request");
        AuthDto authDto = personMapper.authRequestToAuthDto(personRequest);
        log.info("Mapped authentication request: {}", authDto);
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDto.getName(),
                        authDto.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            log.info("Authentication failed");
            throw new IncorrectCredentialsException("Incorrect name or password");
        }
        log.info("Authentication OK");
        String token = jwtUtil.generateToken(authDto.getName());
        log.info("Token successfully generated");
        return new JwtTokenResponse(token);
    }
}

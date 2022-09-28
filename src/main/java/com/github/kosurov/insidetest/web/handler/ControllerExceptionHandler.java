package com.github.kosurov.insidetest.web.handler;

import com.github.kosurov.insidetest.exception.IncorrectCredentialsException;
import com.github.kosurov.insidetest.exception.PersonAlreadyExistsException;
import com.github.kosurov.insidetest.web.response.BaseWebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<BaseWebResponse> handleInvalidInputException(@NonNull final IncorrectCredentialsException exc) {
//        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<BaseWebResponse> handlePersonAlreadyExistsException(@NonNull final PersonAlreadyExistsException exc) {
//        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    private String createErrorMessage(Exception exception) {
        final String message = exception.getMessage();
//        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return message;
    }
}
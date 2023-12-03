package com.example.micrservice.configs;

import com.example.micrservice.exceptions.BadRequestException;
import com.example.micrservice.models.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    protected ErrorModel handleBadRequestException(BadRequestException ex) {
        log.error("Incorrect request, msg: {}", ex.getMessage());
        return ErrorModel.builder()
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    protected ErrorModel handleRuntimeException(RuntimeException ex) {
        log.error("Something went wrong, msg: {}", ex.getMessage(), ex);
        return ErrorModel.builder()
                .message(ex.getMessage())
                .build();
    }
}

package com.kapp.kappcore.web.configuration;

import com.kapp.kappcore.ApiResponse;
import com.kapp.kappcore.constant.ExCode;
import com.kapp.kappcore.exception.SearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class KappExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public Object nullHandler(NullPointerException exception) {
        log.error("Null Point", exception);
        return ApiResponse
                .builder()
                .code(ExCode.error)
                .message("系统错误").build();
    }

    @ExceptionHandler(SearchException.class)
    public Object searchExceptionHandler(SearchException exception) {
        log.error("searchException", exception);
        return ApiResponse
                .builder()
                .code(exception.getExceptionCode())
                .message(exception.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception exception) {
        log.error("searchException", exception);
        return ApiResponse
                .builder()
                .code(ExCode.error)
                .message("系统错误").build();
    }
}

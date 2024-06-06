package com.kapp.kappcore.web.configuration.response;

import com.kapp.kappcore.model.ApiResponse;
import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.exception.BizException;
import com.kapp.kappcore.model.exception.KappException;
import com.kapp.kappcore.model.exception.SearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class KappExceptionHandler {
    @ExceptionHandler(value = {SearchException.class, BizException.class})
    public Object searchExceptionHandler(KappException exception) {
        log.error("exception:", exception);
        return ApiResponse
                .builder()
                .code(exception.getExceptionCode())
                .message(exception.getMessage()).build();
    }
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception exception) {
        log.error("exception", exception);
        return ApiResponse
                .builder()
                .code(ExCode.error)
                .message("系统错误").build();
    }
}

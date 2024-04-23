package com.kapp.kappcore.web.configuration.response;

import com.kapp.kappcore.model.ApiResponse;
import com.kapp.kappcore.model.constant.ExCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class KappResponseAdvice implements ResponseBodyAdvice<Object> {
    private final static String OK = "OK";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return ApiResponse.builder().code(ExCode.success).message(OK).build();
        }
        return ApiResponse.builder().code(ExCode.success).message(OK).data(body).build();
    }
}

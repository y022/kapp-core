package com.kapp.kappcore.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    //...

    /**
     * 拦截所有请求，校验url和token
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    //...
}

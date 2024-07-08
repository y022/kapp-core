package com.kapp.kappcore.model.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BizException extends KappException {
    public BizException(String exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
}

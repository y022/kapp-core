package com.kapp.kappcore.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends KappException {
    public BizException(String exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
}

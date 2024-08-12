package com.kapp.kappcore.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KappException extends RuntimeException{
    protected String exceptionCode;
    protected String message;

    public KappException(String exceptionCode, String message) {
        this.exceptionCode =exceptionCode;
        this.message = message;
    }

    public KappException() {
    }
}

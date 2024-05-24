package com.kapp.kappcore.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchException extends RuntimeException {
    private String exceptionCode;
    private String message;

    public SearchException(String exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

}

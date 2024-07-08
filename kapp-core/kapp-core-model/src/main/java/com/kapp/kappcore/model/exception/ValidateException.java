package com.kapp.kappcore.model.exception;

/**
 * Author:Heping
 * Date: 2024/7/8 13:01
 */
public class ValidateException extends KappException {
    public ValidateException(String exceptionCode, String message) {
        super(exceptionCode, message);
    }
}

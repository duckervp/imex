package com.svc.pr.domain.exception;

public class ValidateException extends RuntimeException {
    public ValidateException(String message) {
        super(message);
    }
}

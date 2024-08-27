package com.common.exceptions;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
public class CreateExpenseException extends RuntimeException {
    private final HttpStatus status;
    private final String error;

    public CreateExpenseException(String message, HttpStatus status, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }
}

package ru.javawebinar.topjava.util.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException{

    public ValidationException(BindingResult result) {
        super(getMessage(result));
    }

    private static String getMessage(BindingResult result) {
        return result.toString();
    }
}


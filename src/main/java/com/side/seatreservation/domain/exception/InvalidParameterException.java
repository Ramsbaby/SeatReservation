package com.side.seatreservation.domain.exception;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class InvalidParameterException extends RuntimeException {

    private final List<ObjectError> errors;

    public InvalidParameterException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }
}

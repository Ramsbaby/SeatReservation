package com.side.seatreservation.domain.exception;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class CustomValidationException extends RuntimeException {

    public CustomValidationException(String message) {
        super(message);
    }


}

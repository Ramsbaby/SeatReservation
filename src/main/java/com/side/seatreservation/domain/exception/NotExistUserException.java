package com.side.seatreservation.domain.exception;

import lombok.Getter;

@Getter
public class NotExistUserException extends RuntimeException {

    public NotExistUserException(String message) {
        super(message);
    }
}

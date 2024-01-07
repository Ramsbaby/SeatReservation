package com.side.seatreservation.domain.exception;

import lombok.Getter;

@Getter
public class NotExistSeatException extends RuntimeException {

    public NotExistSeatException(String message) {
        super(message);
    }
}

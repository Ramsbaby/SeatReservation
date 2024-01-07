package com.side.seatreservation.domain.exception;

import lombok.Getter;

@Getter
public class AlreadyExistSeatException extends RuntimeException {

    public AlreadyExistSeatException(String message) {
        super(message);
    }
}

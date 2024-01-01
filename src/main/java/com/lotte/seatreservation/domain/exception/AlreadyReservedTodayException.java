package com.lotte.seatreservation.domain.exception;

import lombok.Getter;

@Getter
public class AlreadyReservedTodayException extends RuntimeException {

    public AlreadyReservedTodayException(String message) {
        super(message);
    }
}

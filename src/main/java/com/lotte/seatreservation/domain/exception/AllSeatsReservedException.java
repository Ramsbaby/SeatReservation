package com.lotte.seatreservation.domain.exception;

import lombok.Getter;

@Getter
public class AllSeatsReservedException extends RuntimeException {

    public AllSeatsReservedException(String message) {
        super(message);
    }
}

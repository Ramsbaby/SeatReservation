package com.lotte.seatreservation.domain.model.seat;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatReserveRequestDto {

    @Min(0)
    private Long seatId;

    @Min(0)
    private Long userId;
}
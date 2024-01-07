package com.side.seatreservation.presentation;

import com.side.seatreservation.application.services.SeatService;
import com.side.seatreservation.domain.exception.InvalidParameterException;
import com.side.seatreservation.domain.model.seat.SeatReserveRequestDto;
import com.side.seatreservation.domain.model.seat.SeatResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/reserve")
    public Mono<SeatResponseDto> reserve(@RequestBody @Valid SeatReserveRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParameterException("invalid parameter", bindingResult.getAllErrors());
        }

        return seatService.reserveSeat(request.getSeatId(), request.getUserId());
    }

    @DeleteMapping("/cancel")
    public void remove(@RequestParam("userId") Long userId,
                       @RequestParam("seatId") Long seatId) {
        seatService.removeSeat(userId, seatId);
    }
}

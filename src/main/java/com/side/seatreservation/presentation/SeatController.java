package com.side.seatreservation.presentation;

import com.side.seatreservation.application.services.SeatService;
import com.side.seatreservation.domain.exception.CustomValidationException;
import com.side.seatreservation.domain.exception.InvalidParameterException;
import com.side.seatreservation.domain.model.seat.SeatReserveRequestDto;
import com.side.seatreservation.domain.model.seat.SeatResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/reserve")
    public Mono<SeatResponseDto> reserve(@RequestBody Mono<SeatReserveRequestDto> request) {
        return request
                .flatMap(req -> seatService.reserveSeat(req.getSeatId(), req.getUserId()))
                .onErrorResume(WebExchangeBindException.class, e -> {
                    // 유효성 검사 실패에 대한 처리
                    return Mono.error(new CustomValidationException("Validation failed" + e.getMessage()));
                });

    }

    @DeleteMapping("/cancel")
    public Mono<Void> remove(@RequestParam("userId") Long userId,
                             @RequestParam("seatId") Long seatId) {
        return seatService.removeSeat(userId, seatId);
    }
}

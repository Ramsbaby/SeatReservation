package com.side.seatreservation.infrastructure.repository;

import com.side.seatreservation.domain.model.history.SeatReservationHistory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SeatReservationHistoryRepository extends R2dbcRepository<SeatReservationHistory, Long> {
    Mono<SeatReservationHistory> findBySeatIdAndUserId(Long seatId, Long userId);
}

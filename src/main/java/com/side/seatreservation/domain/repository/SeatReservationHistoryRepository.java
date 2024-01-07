package com.side.seatreservation.domain.repository;

import com.side.seatreservation.domain.model.history.SeatReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface SeatReservationHistoryRepository extends ReactiveCrudRepository<SeatReservationHistory, Long> {
    Mono<SeatReservationHistory> findBySeatIdAndUserId(Long seatId, Long userId);
}

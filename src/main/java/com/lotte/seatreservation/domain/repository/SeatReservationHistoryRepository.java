package com.lotte.seatreservation.domain.repository;

import com.lotte.seatreservation.domain.model.history.SeatReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatReservationHistoryRepository extends JpaRepository<SeatReservationHistory, Long> {
    Optional<SeatReservationHistory> findBySeatIdAndUserId(Long seatId, Long userId);
}

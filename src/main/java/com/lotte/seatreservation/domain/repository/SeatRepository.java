package com.lotte.seatreservation.domain.repository;

import com.lotte.seatreservation.domain.model.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    int countByReservedDate(LocalDate reservedDate);

}

package com.side.seatreservation.infrastructure.repository;

import com.side.seatreservation.domain.model.seat.Seat;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface SeatRepository extends R2dbcRepository<Seat, Long> {
    Mono<Integer> countByReservedDate(LocalDate reservedDate);

}

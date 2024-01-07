package com.side.seatreservation.domain.repository;

import com.side.seatreservation.domain.model.seat.Seat;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface SeatRepository extends ReactiveCrudRepository<Seat, Long> {
    Mono<Integer> countByReservedDate(LocalDate reservedDate);

}

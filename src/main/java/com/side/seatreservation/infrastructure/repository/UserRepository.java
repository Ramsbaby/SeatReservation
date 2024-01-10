package com.side.seatreservation.infrastructure.repository;

import com.side.seatreservation.domain.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends R2dbcRepository<User, Long> {
    Flux<User> findAllBy(Pageable pageable);
}

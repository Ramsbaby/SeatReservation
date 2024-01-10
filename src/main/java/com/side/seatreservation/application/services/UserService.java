package com.side.seatreservation.application.services;

import com.side.seatreservation.domain.exception.NotExistUserException;
import com.side.seatreservation.domain.model.user.User;
import com.side.seatreservation.domain.model.user.UserResponseDto;
import com.side.seatreservation.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final R2dbcEntityTemplate template;

    public Flux<UserResponseDto> findAllUsersWithPagination(Pageable pageable) {
        return template.select(User.class)
                .matching(Query.query(Criteria.empty()).with(pageable))
                .all()
                .map(this::convertToDto);

    }

    private UserResponseDto convertToDto(User user) {
        Long seatId = user.getReservedSeat() == null ? null : user.getReservedSeat().getSeatId();
        return new UserResponseDto(user.getUserId(), user.getName(), user.getWorkType(), seatId);
    }

    public Mono<User> findUserById(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotExistUserException("유저가 존재하지 않습니다.")));
    }
}

package com.side.seatreservation.application.services;

import com.side.seatreservation.domain.exception.NotExistUserException;
import com.side.seatreservation.domain.model.user.User;
import com.side.seatreservation.domain.model.user.UserResponseDto;
import com.side.seatreservation.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Flux<UserResponseDto> findAllUsersWithPagination(Pageable pageable) {
        return userRepository.findAll(pageable) // TODO: 수정해야됨
                .map(this::convertToDto); // 엔티티를 DTO로 변환
    }

    private UserResponseDto convertToDto(User user) {
        Long seatId = user.getReservedSeat() == null ? null : user.getReservedSeat().getSeatId();
        return new UserResponseDto(user.getUserId(), user.getName(), user.getWorkType(), seatId);
    }

    @Transactional(readOnly = true)
    public Mono<User> findUserById(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotExistUserException("유저가 존재하지 않습니다.")));
    }
//
//
//    @Transactional(readOnly = true)
//    public List<UserResponseDto> findAllUsersWithPagination(Pageable pageable) {
//        Page<User> userPage = userRepository.findAll(pageable);
//
//        return userPage.getContent()
//                .stream()
//                .map(this::convertToDto) // 엔티티를 DTO로 변환
//                .collect(Collectors.toList());
//    }
//
//
//    private UserResponseDto convertToDto(User user) {
//        Long seatId = user.getReservedSeat() == null ? null : user.getReservedSeat().getSeatId();
//        return new UserResponseDto(user.getUserId(), user.getName(), user.getWorkType(), seatId);
//    }
//
//
//    @Transactional(readOnly = true)
//    public Mono<User> findUserById(Long userId) {
//        return userRepository.findById(userId)
//                .switchIfEmpty(Mono.error(new NotExistUserException("유저가 존재하지 않습니다.")));
//
//    }
}

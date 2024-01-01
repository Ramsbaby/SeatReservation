package com.lotte.seatreservation.application.services;

import com.lotte.seatreservation.domain.exception.NotExistUserException;
import com.lotte.seatreservation.domain.model.user.User;
import com.lotte.seatreservation.domain.model.user.UserResponseDto;
import com.lotte.seatreservation.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUsersWithPagination(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.getContent()
                .stream()
                .map(this::convertToDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }


    private UserResponseDto convertToDto(User user) {
        Long seatId = user.getReservedSeat() == null ? null : user.getReservedSeat().getSeatId();
        return new UserResponseDto(user.getUserId(), user.getName(), user.getWorkType(), seatId);
    }


    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotExistUserException("유저가 존재하지 않습니다."));
    }
}

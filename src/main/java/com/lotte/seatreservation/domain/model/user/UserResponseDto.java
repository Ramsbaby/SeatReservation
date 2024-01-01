package com.lotte.seatreservation.domain.model.user;

public record UserResponseDto(Long userId, String userName, WorkType workType, Long seatId) {
}
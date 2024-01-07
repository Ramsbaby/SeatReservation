package com.side.seatreservation.domain.model.user;

public record UserResponseDto(Long userId, String userName, WorkType workType, Long seatId) {
}
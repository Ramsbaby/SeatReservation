package com.side.seatreservation.application.services;

import com.side.seatreservation.domain.exception.AlreadyExistSeatException;
import com.side.seatreservation.domain.exception.AlreadyReservedTodayException;
import com.side.seatreservation.domain.exception.NotExistSeatException;
import com.side.seatreservation.domain.model.history.SeatReservationHistory;
import com.side.seatreservation.domain.model.seat.Seat;
import com.side.seatreservation.domain.model.seat.SeatResponseDto;
import com.side.seatreservation.domain.model.user.User;
import com.side.seatreservation.domain.model.user.WorkType;
import com.side.seatreservation.domain.repository.SeatRepository;
import com.side.seatreservation.domain.repository.SeatReservationHistoryRepository;
import com.side.seatreservation.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final SeatReservationHistoryRepository historyRepository;
    private final UserService userService;

    private final int TOTAL_SEAT_SIZE = 2;

    //    @Transactional
//    public Mono<SeatResponseDto> reserveSeat(Long seatId, Long userId) {
//        User user = userService.findUserById(userId);
//
//        Seat seat = entityManager.find(Seat.class, seatId, LockModeType.PESSIMISTIC_WRITE);
//
//        LocalDate today = LocalDate.now();
//
//        int reservedSeatsCount = seatRepository.countByReservedDate(today);
//
//        if (reservedSeatsCount >= TOTAL_SEAT_SIZE) {
//            user.setWorkType(WorkType.REMOTE);
//            userRepository.save(user);
//            return new SeatResponseDto(seatId, userId);
//        }
//
//        if (seat != null) {
//            Optional<SeatReservationHistory> history = historyRepository.findBySeatIdAndUserId(seatId, userId);
//            if (history.isPresent()) {//하루에 한번 save 가능 검사로직
//                if (history.get().getReservationDate().equals(seat.getReservedDate())) {
//                    throw new AlreadyReservedTodayException("Seat is already reserved today by Same Id: " + seatId);
//                }
//            }
//
//            throw new AlreadyExistSeatException("Seat is already reserved with user id: " + seat.getReservedBy().getUserId());
//        }
//
//        seat = Seat.of(seatId, user);
//        user.setWorkType(WorkType.OFFICE);
//        userRepository.save(user);
//        seatRepository.save(seat);
//        saveHistory(seatId, userId);
//
//        return new SeatResponseDto(seatId, userId);
//    }
    @Transactional
    public Mono<SeatResponseDto> reserveSeat(Long seatId, Long userId) {
        return userService.findUserById(userId)
                .flatMap(user -> reserveSeatForUser(seatId, user));
    }

    private Mono<SeatResponseDto> reserveSeatForUser(Long seatId, User user) {
        return seatRepository.findById(seatId)
                .flatMap(seat -> checkAndReserveSeat(seat, user))
                .switchIfEmpty(Mono.defer(() -> createAndReserveSeat(seatId, user)))
                .map(seat -> new SeatResponseDto(seat.getSeatId(), user.getUserId()));
    }

    private Mono<Boolean> checkReservationHistory(Seat seat, User user) {
        return historyRepository.findBySeatIdAndUserId(seat.getSeatId(), user.getUserId())
                .map(history -> history.getReservationDate().equals(seat.getReservedDate()));
    }

    private Mono<Seat> checkAndReserveSeat(Seat seat, User user) {
        return checkReservationHistory(seat, user)
                .flatMap(historyExists -> {
                    if (historyExists) {
                        return Mono.error(new AlreadyReservedTodayException("Seat is already reserved today by Same Id: " + seat.getSeatId()));
                    }
                    return updateReservationForSeat(seat, user);
                });
    }

    private Mono<Seat> updateReservationForSeat(Seat seat, User user) {
        // 예약 가능 여부 확인
        return seatRepository.countByReservedDate(LocalDate.now())
                .flatMap(count -> {
                    if (count >= TOTAL_SEAT_SIZE) {
                        user.setWorkType(WorkType.REMOTE);
                        return userRepository.save(user).thenReturn(seat);
                    }
                    // 좌석 예약 업데이트
                    seat.setReservedBy(user);
                    seat.setReservedDate(LocalDate.now());
                    return seatRepository.save(seat);
                });
    }

    private Mono<Seat> createAndReserveSeat(Long seatId, User user) {
        Seat newSeat = new Seat(seatId, user, LocalDate.now()); // 가정: Seat 생성자
        return seatRepository.save(newSeat)
                .flatMap(seat -> {
                    // 사용자 상태 업데이트 및 역사 기록 저장
                    user.setWorkType(WorkType.OFFICE);
                    return userRepository.save(user)
                            .then(saveHistory(seat.getSeatId(), user.getUserId()))
                            .thenReturn(seat);
                });
    }

    @Transactional
    public Mono<Void> removeSeat(Long userId, Long seatId) {
        return seatRepository.findById(seatId)
                .switchIfEmpty(Mono.error(new NotExistSeatException("Seat not found with id: " + seatId)))
                .flatMap(seat -> {
                    if (seat.getReservedBy() != null && !seat.getReservedBy().getUserId().equals(userId)) {
                        return Mono.error(new AlreadyExistSeatException("Seat is already reserved by other user with id: " + seat.getReservedBy().getUserId()));
                    }
                    return Mono.just(seat);
                })
                .flatMap(seat -> {
                    if (seat.getReservedBy() != null) {
                        User user = seat.getReservedBy();
                        user.setReservedSeat(null);
                        return userRepository.save(user).then(seatRepository.delete(seat));
                    } else {
                        return seatRepository.delete(seat);
                    }
                });
    }

    public Mono<Void> saveHistory(Long seatId, Long userId) {
        SeatReservationHistory history = new SeatReservationHistory();
        history.setSeatId(seatId);
        history.setUserId(userId);
        history.setReservationDate(LocalDate.now());
        history.setActive(true); // 예약 활성화
        return historyRepository.save(history).then();
    }
}

package com.lotte.seatreservation.application.services;

import com.lotte.seatreservation.domain.exception.AllSeatsReservedException;
import com.lotte.seatreservation.domain.exception.AlreadyExistSeatException;
import com.lotte.seatreservation.domain.exception.AlreadyReservedTodayException;
import com.lotte.seatreservation.domain.exception.NotExistSeatException;
import com.lotte.seatreservation.domain.model.history.SeatReservationHistory;
import com.lotte.seatreservation.domain.model.seat.Seat;
import com.lotte.seatreservation.domain.model.seat.SeatResponseDto;
import com.lotte.seatreservation.domain.model.user.User;
import com.lotte.seatreservation.domain.model.user.WorkType;
import com.lotte.seatreservation.domain.repository.SeatRepository;
import com.lotte.seatreservation.domain.repository.SeatReservationHistoryRepository;
import com.lotte.seatreservation.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final SeatReservationHistoryRepository historyRepository;
    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    private final int TOTAL_SEAT_SIZE = 2;

    @Transactional
    public SeatResponseDto reserveSeat(Long seatId, Long userId) {
        User user = userService.findUserById(userId);

        Seat seat = entityManager.find(Seat.class, seatId, LockModeType.PESSIMISTIC_WRITE);

        LocalDate today = LocalDate.now();

        int reservedSeatsCount = seatRepository.countByReservedDate(today);

        if (reservedSeatsCount >= TOTAL_SEAT_SIZE) {
            user.setWorkType(WorkType.REMOTE);
            userRepository.save(user);
            return new SeatResponseDto(seatId, userId);
        }

        if (seat != null) {
            Optional<SeatReservationHistory> history = historyRepository.findBySeatIdAndUserId(seatId, userId);
            if (history.isPresent()) {//하루에 한번 save 가능 검사로직
                if (history.get().getReservationDate().equals(seat.getReservedDate())) {
                    throw new AlreadyReservedTodayException("Seat is already reserved today by Same Id: " + seatId);
                }
            }

            throw new AlreadyExistSeatException("Seat is already reserved with user id: " + seat.getReservedBy().getUserId());
        }

        seat = Seat.of(seatId, user);
        user.setWorkType(WorkType.OFFICE);
        userRepository.save(user);
        seatRepository.save(seat);
        saveHistory(seatId, userId);

        return new SeatResponseDto(seatId, userId);
    }

    public List<Seat> searchAllSeats() {
        return seatRepository.findAll();
    }

    @Transactional
    public void removeSeat(Long userId, Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new NotExistSeatException("Seat not found with id: " + seatId));

        if (seat.getReservedBy() != null && !seat.getReservedBy().getUserId().equals(userId)) {
            throw new AlreadyExistSeatException("Seat is already reserved by other user with id: " + seat.getReservedBy().getUserId());
        }

        if (seat.getReservedBy() != null) {
            User user = seat.getReservedBy();
            user.setReservedSeat(null);
            userRepository.save(user);
        }

        seatRepository.delete(seat);
    }


    @Transactional
    public void saveHistory(Long seatId, Long userId) {
        SeatReservationHistory history = new SeatReservationHistory();
        history.setSeatId(seatId);
        history.setUserId(userId);
        history.setReservationDate(LocalDate.now());
        history.setActive(true); // 예약 활성화
        historyRepository.save(history);
    }
}

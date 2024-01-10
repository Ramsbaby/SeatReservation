package com.side.seatreservation.domain.model.history;

import com.side.seatreservation.domain.common.CommonEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "histories")
@EqualsAndHashCode(callSuper = true)
public class SeatReservationHistory extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seatId;
    private Long userId;
    private LocalDate reservationDate;
    private boolean isActive;
}

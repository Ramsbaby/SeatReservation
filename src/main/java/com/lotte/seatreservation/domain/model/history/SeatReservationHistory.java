package com.lotte.seatreservation.domain.model.history;


import com.lotte.seatreservation.domain.common.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
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

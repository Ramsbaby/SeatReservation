package com.side.seatreservation.domain.model.seat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.side.seatreservation.domain.common.CommonEntity;
import com.side.seatreservation.domain.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seats")
@EqualsAndHashCode(callSuper = true)
public class Seat extends CommonEntity {

    @Id
    private Long seatId;

    @OneToOne(mappedBy = "reservedSeat")
    @JsonBackReference
    private User reservedBy;

    @Column
    private LocalDate reservedDate;

    public static Seat of(Long seatId, User user) {
        Seat seat = Seat.builder()
                .seatId(seatId)
                .reservedBy(user)
                .reservedDate(LocalDate.now())
                .build();

        seat.setCreatedAt(LocalDateTime.now());
        user.setReservedSeat(seat);

        return seat;
    }

    public boolean isReserved() {
        return this.reservedBy != null;
    }
}

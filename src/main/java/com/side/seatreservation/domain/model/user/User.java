package com.side.seatreservation.domain.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.side.seatreservation.domain.common.CommonEntity;
import com.side.seatreservation.domain.model.seat.Seat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private WorkType workType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id")
    @JsonManagedReference
    private Seat reservedSeat;
}

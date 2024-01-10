package com.side.seatreservation.domain.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.side.seatreservation.domain.common.CommonEntity;
import com.side.seatreservation.domain.model.seat.Seat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends CommonEntity {
    @Id
    private Long userId;

    private String name;

    private WorkType workType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id")
    @JsonManagedReference
    private Seat reservedSeat;
}

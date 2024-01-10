package com.side.seatreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
public class SeatReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatReservationApplication.class, args);
    }

}

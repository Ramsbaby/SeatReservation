package com.side.seatreservation.domain.model.user;

import lombok.Getter;

@Getter
public enum WorkType {
    OFFICE("o"),
    REMOTE("r"),
    VACATION("v");

    private final String type;

    WorkType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}

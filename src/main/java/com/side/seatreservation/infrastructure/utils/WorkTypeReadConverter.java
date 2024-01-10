package com.side.seatreservation.infrastructure.utils;

import com.side.seatreservation.domain.model.user.WorkType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class WorkTypeReadConverter implements Converter<String, WorkType> {
    @Override
    public WorkType convert(String dbData) {

        return switch (dbData) {
            case "o" -> WorkType.OFFICE;
            case "r" -> WorkType.REMOTE;
            case "v" -> WorkType.VACATION;
            default -> throw new IllegalArgumentException("Unknown value: " + dbData);
        };
    }
}
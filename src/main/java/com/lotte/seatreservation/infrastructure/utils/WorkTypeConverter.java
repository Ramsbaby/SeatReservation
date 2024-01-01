package com.lotte.seatreservation.infrastructure.utils;

import com.lotte.seatreservation.domain.model.user.WorkType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WorkTypeConverter implements AttributeConverter<WorkType, String> {

    @Override
    public String convertToDatabaseColumn(WorkType workType) {
        if (workType == null) {
            return null;
        }
        return workType.getType();
    }

    @Override
    public WorkType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return switch (dbData) {
            case "o" -> WorkType.OFFICE;
            case "r" -> WorkType.REMOTE;
            case "v" -> WorkType.VACATION;
            default -> throw new IllegalArgumentException("Unknown value: " + dbData);
        };
    }
}

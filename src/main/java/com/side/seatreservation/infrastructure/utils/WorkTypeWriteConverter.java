package com.side.seatreservation.infrastructure.utils;

import com.side.seatreservation.domain.model.user.WorkType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class WorkTypeWriteConverter implements Converter<WorkType, String> {
    @Override
    public String convert(WorkType workType) {
        return workType.getType();
    }
}

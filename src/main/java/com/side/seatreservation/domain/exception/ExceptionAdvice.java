package com.side.seatreservation.domain.exception;

import com.side.seatreservation.domain.common.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(e.getMessage())
                .setFieldErrorData(e.getAllErrors())
                .build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidParameterException(InvalidParameterException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = NotExistUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotExistUserException(NotExistUserException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.NOT_FOUND.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = NotExistSeatException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotExistSeatException(NotExistSeatException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.NOT_FOUND.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = AlreadyExistSeatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNotExistSeatException(AlreadyExistSeatException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = AlreadyReservedTodayException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNotExistSeatException(AlreadyReservedTodayException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = AllSeatsReservedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNotExistSeatException(AllSeatsReservedException e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse GeneralException(Exception e) {
        return new ApiErrorResponse.Builder()
                .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setMsg(e.getMessage())
                .build();
    }
}

package com.example.demo.advice;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);


    @ExceptionHandler(value = { BadRequestException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception) {
        logger.error("Error: ", exception);
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(exception.getCode());
        errorResponse.setMessage(exception.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleJCannotCreateTransactionException(Exception exception) {
        logger.error("Error: ", exception);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode("Server Error");
        errorResponse.setMessage("Server Error");

        return errorResponse;
    }
}

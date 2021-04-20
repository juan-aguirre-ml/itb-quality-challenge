package com.itbqualitychallenge.bookings.controllers;

import com.itbqualitychallenge.bookings.dtos.StatusCodeDTO;
import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionController {

    @ExceptionHandler({QueryValidationException.class})
    public ResponseEntity<StatusCodeDTO> badQuery(RuntimeException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(new StatusCodeDTO(400, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({QueryValidationException.class})
    public ResponseEntity<StatusCodeDTO> badRequest(RuntimeException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(new StatusCodeDTO(400, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}


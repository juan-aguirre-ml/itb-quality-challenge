package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.FlightQueryDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FlightQueryDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return FlightQueryDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //TODO Implement the validation
    }
}

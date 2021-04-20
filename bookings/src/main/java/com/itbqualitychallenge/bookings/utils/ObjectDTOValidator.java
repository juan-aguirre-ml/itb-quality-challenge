package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.exceptions.InvalidDateRangeException;
import com.itbqualitychallenge.bookings.exceptions.InvalidDestinationException;
import com.itbqualitychallenge.bookings.exceptions.ParameterQuantityException;
import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;

public interface ObjectDTOValidator<T>{
    T validate(T object) throws QueryValidationException, InvalidDestinationException, InvalidDateRangeException, ParameterQuantityException;
}

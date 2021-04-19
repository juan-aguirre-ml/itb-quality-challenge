package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;

public interface ObjectDTOValidator<T>{
    public T validate(T object) throws QueryValidationException;
}

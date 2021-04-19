package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;

public interface PayloadValidator<T> {
    public void validatePayload(T payload) throws QueryValidationException;
}

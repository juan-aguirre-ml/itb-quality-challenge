package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.exceptions.*;

public interface PayloadValidator<T> {
    public void validatePayload(T payload) throws QueryValidationException, InvalidCardNumberException, CreditDuesException, DebitDuesException, InvalidEmailException, TooMuchPeopleException, InvalidDestinationException, InvalidDateRangeException, PayloadValidationException;
}

package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.exceptions.*;

public interface PayloadValidator<T> {
    void validatePayload(T payload) throws QueryValidationException, InvalidCardNumberException, CreditDuesException, DebitDuesException, InvalidEmailException, TooMuchPeopleException, InvalidDestinationException, InvalidDateRangeException, PayloadValidationException;
}

package com.itbqualitychallenge.bookings.exceptions;

import org.springframework.beans.factory.annotation.Value;

public class TooMuchPeopleException extends Exception{
    public TooMuchPeopleException(String s) {
        super(s);
    }
}

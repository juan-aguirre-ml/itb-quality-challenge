package com.itbqualitychallenge.bookings.dtos;

import lombok.Data;

@Data
public class PaymentMethodDTO {
    private String type;
    private String number;
    private int dues;
}

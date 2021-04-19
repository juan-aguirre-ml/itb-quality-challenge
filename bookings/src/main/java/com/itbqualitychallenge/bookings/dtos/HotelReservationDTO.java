package com.itbqualitychallenge.bookings.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReservationDTO {
    private String username;
    private float amount;
    private float interest;
    private float total;

    private HotelBookingDTO booking;
    private StatusCodeDTO statusCode;

}

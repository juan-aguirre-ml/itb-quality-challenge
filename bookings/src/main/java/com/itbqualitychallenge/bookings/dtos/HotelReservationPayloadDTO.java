package com.itbqualitychallenge.bookings.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReservationPayloadDTO {
    private String userName;
    private HotelBookingDTO booking;
}

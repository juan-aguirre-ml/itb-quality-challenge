package com.itbqualitychallenge.bookings.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private String price;
    private String departureDate;
    private String returnDate;

}

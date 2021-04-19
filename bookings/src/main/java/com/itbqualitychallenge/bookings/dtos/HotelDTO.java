package com.itbqualitychallenge.bookings.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private String hotelCode;
    private String hotelName;
    private String location;
    private String roomType;
    private String pricePerNight;
    private String availableFrom;
    private String availableTo;
    private Boolean isReserved;
}

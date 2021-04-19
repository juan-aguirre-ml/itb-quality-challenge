package com.itbqualitychallenge.bookings.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelQueryDTO {
    private String dateFrom;
    private String dateTo;
    private String destination;
}

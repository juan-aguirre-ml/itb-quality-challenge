package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.exceptions.InvalidDateRangeException;
import com.itbqualitychallenge.bookings.exceptions.InvalidDestinationException;
import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HotelQueryDTOValidator implements ObjectDTOValidator{

    @Override
    public HotelQueryDTO validate(Object o) throws QueryValidationException {

        HotelQueryDTO query = (HotelQueryDTO) o;
        if (query == null){
            return query;
        }
        try {
            LocalDate dateFrom;
            LocalDate dateTo;
            if (query.getDateFrom()!= null|| query.getDateTo()!=null) {
                dateFrom = LocalDate.parse(query.getDateFrom(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                dateTo = LocalDate.parse(query.getDateTo(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (dateFrom.isAfter(dateTo) || dateTo.isBefore(dateFrom)){
                    throw new InvalidDateRangeException("${hotel.date.range.invalid}");
                }
            }
            if (query.getDestination().isBlank() || query.getDestination().isEmpty()) {
                throw new InvalidDestinationException("${hotel.destination.invalid}");
            }

            return query;
        } catch (InvalidDestinationException | DateTimeParseException | InvalidDateRangeException e) {
            throw new QueryValidationException(e.getMessage());
        }

    }

}

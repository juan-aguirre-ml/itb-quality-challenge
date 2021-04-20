package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.exceptions.InvalidDateRangeException;
import com.itbqualitychallenge.bookings.exceptions.InvalidDestinationException;
import com.itbqualitychallenge.bookings.exceptions.ParameterQuantityException;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public abstract class HotelQueryDTOValidator{
    @Value("${hotel.validation.date.range.invalid}")
    private static String hotelDateRangeInvalid;
    @Value("${hotel.validation.destination.invalid}")
    private static String hotelDestinationInvalid;


    public static HotelQueryDTO validate(Object queryParams) throws InvalidDestinationException, InvalidDateRangeException, DateTimeParseException, ParameterQuantityException {


        HashMap<String,String> queryHash = (HashMap<String,String>) queryParams;
        HotelQueryDTO query = new HotelQueryDTO(queryHash.get("dateFrom"),queryHash.get("dateTo"),queryHash.get("destination"));
        //Can only accept zero or 3 parameters
        if (queryHash.size() == 3) {
            //LocalDate throws a parsing exception
            LocalDate dateFrom = LocalDate.parse(query.getDateFrom(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate dateTo = LocalDate.parse(query.getDateTo(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            //Check for range validity
            if (dateFrom.isAfter(dateTo) || dateTo.isBefore(dateFrom)) {
                throw new InvalidDateRangeException(hotelDateRangeInvalid);
            }
            if (query.getDestination() != null && query.getDestination().isEmpty()) {
                throw new InvalidDestinationException(hotelDestinationInvalid);
            }
        }else if (queryHash.size() == 0){
            return new HotelQueryDTO();
        }else
            throw new ParameterQuantityException("Parameter quantity invalid.");
        return query;


    }

}

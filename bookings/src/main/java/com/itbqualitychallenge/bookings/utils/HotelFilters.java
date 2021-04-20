package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HotelFilters{
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public boolean meetsCondition(HotelDTO hotel, String stringDateFrom, String stringDateTo, String destination){
            LocalDate dateFrom = LocalDate.parse(stringDateFrom,DATE_FORMAT);
            LocalDate dateTo = LocalDate.parse(stringDateTo,DATE_FORMAT);
            LocalDate hotelAvailableFrom = LocalDate.parse(hotel.getAvailableFrom(),DATE_FORMAT);
            LocalDate hotelAvailableTo = LocalDate.parse(hotel.getAvailableTo(),DATE_FORMAT);
            return destination.equals(hotel.getLocation()) &&
                    (dateFrom.isAfter(hotelAvailableFrom) || dateFrom.isEqual(hotelAvailableFrom)) &&
                    (dateTo.isBefore(hotelAvailableTo) || dateTo.isEqual(hotelAvailableTo));

    }

    public ArrayList<HotelDTO> filter(ArrayList<HotelDTO> arr, HotelQueryDTO query){

        if (query != null && query.getDestination()!=null && query.getDateFrom()!= null && query.getDateTo()!= null ) {
            ArrayList<HotelDTO> tmp = new ArrayList<>();
            for (HotelDTO hotel:arr){
                if (meetsCondition(hotel,query.getDateFrom(),query.getDateTo(),query.getDestination())){
                    tmp.add(hotel);
                }
            }
            arr = (ArrayList<HotelDTO>) tmp.stream().filter(e->e.getIsReserved().equals(false)).collect(Collectors.toList());
            return arr;

        }return arr;

    }

}

package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class HotelFilters{
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static ArrayList<HotelDTO> applyFilter(ArrayList<HotelDTO> coll, Predicate<HotelDTO> predicate) {
        if (predicate != null)
            return (ArrayList<HotelDTO>) coll.stream().filter(predicate).collect(Collectors.toList());
        return coll;

    }

    public static Predicate<HotelDTO> getFilter(String type, String query) {

        switch (type){
            case "dateFrom":
                return e -> LocalDate.parse(query,DATE_FORMAT).isAfter(LocalDate.parse(e.getAvailableFrom(),DATE_FORMAT).minusDays(1));
            case "dateTo":
                return e -> LocalDate.parse(query,DATE_FORMAT).isBefore(LocalDate.parse(e.getAvailableTo(),DATE_FORMAT).plusDays(1));
            case "destination":
                return e -> e.getLocation().equals(query);
            default:
                return null;
        }
    }

    public static void filter(ArrayList<HotelDTO> arr, HotelQueryDTO query){

        if (query != null) {
            Predicate<HotelDTO> p1 = getFilter("dateFrom", query.getDateFrom());
            Predicate<HotelDTO> p2 = getFilter("dateTo", query.getDateTo());
            Predicate<HotelDTO> p3 = getFilter("destination", query.getDestination());
            /*
            Predicate<HotelDTO> p4 = e -> LocalDate.parse(query.getDateFrom(),DATE_FORMAT).isBefore(LocalDate.parse(e.getAvailableTo(),DATE_FORMAT).plusDays(1)) && LocalDate.parse(query.getDateTo(),DATE_FORMAT).isAfter(LocalDate.parse(e.getAvailableFrom(),DATE_FORMAT).minusDays(1));

            if (!query.getDateFrom().isBlank() && !query.getDateTo().isBlank()){
                applyFilter(arr,p4);
                if (!query.getDestination().isEmpty())
                    applyFilter(arr,p3);
            }else {
                applyFilter(arr, p1);
                applyFilter(arr, p2);
                applyFilter(arr, p3);
            }

             */
            applyFilter(arr, p1);
            applyFilter(arr, p2);
            applyFilter(arr, p3);
        }

    }

}

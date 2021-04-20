package com.itbqualitychallenge.bookings.services;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationPayloadDTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface HotelsReservationService {

    ArrayList<HotelDTO> getHotelsFromRepo(HotelQueryDTO query) throws FileNotFoundException;
    HotelReservationDTO makeHotelReservation(HotelReservationPayloadDTO hotelPayload) throws Exception;
}

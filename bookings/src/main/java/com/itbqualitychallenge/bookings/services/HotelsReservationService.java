package com.itbqualitychallenge.bookings.services;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationPayloadDTO;
import com.itbqualitychallenge.bookings.repositories.HotelsReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface HotelsReservationService {

    public ArrayList<HotelDTO> getHotelsFromRepo(HotelQueryDTO query) throws FileNotFoundException;
    public HotelReservationDTO makeHotelReservation(HotelReservationPayloadDTO hotelPayload) throws Exception;
}

package com.itbqualitychallenge.bookings.repositories;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;

import java.util.ArrayList;

public interface HotelsReservationRepository extends BaseRepository{

    ArrayList<HotelDTO> getAll();
    void setAsReserved(String hotelCode);
    HotelDTO getItemById(String id);


}

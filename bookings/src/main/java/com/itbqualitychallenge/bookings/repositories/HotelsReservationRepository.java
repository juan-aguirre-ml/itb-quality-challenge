package com.itbqualitychallenge.bookings.repositories;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;

import java.util.ArrayList;

public interface HotelsReservationRepository extends BaseRepository{

    public ArrayList<HotelDTO> getAll();
    public void setAsReserved(String hotelCode);

}

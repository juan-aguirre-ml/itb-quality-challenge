package com.itbqualitychallenge.bookings.repositories;

import com.itbqualitychallenge.bookings.dtos.FlightDTO;

import java.util.ArrayList;

public interface FlightsReservationRepository extends BaseRepository {
    public ArrayList<FlightDTO> getAll();
}

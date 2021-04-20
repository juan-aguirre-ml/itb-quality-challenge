package com.itbqualitychallenge.bookings.repositories;

import com.itbqualitychallenge.bookings.dtos.FlightDTO;

import java.util.ArrayList;

public interface FlightsReservationRepository extends BaseRepository {
    ArrayList<FlightDTO> getAll();
    FlightDTO getItemById(String id);

}

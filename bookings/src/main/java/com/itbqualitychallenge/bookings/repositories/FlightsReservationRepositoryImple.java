package com.itbqualitychallenge.bookings.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbqualitychallenge.bookings.dtos.FlightDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class FlightsReservationRepositoryImple implements FlightsReservationRepository{
    private final HashMap<String, FlightDTO> flightRepo = new HashMap<>();

    @Value("${dbFlights}")
    private String filename;

    public Object loadFromFile() {
        File file = null;
        try {
            file = ResourceUtils.getFile(this.filename);

        }catch (FileNotFoundException e){
            //TODO: Fix this
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<FlightDTO>> typeRef = new TypeReference<ArrayList<FlightDTO>>() {
        };
        ArrayList<FlightDTO> db = null;

        try {
            db = mapper.readValue(file,typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (FlightDTO flight:db){
            flightRepo.put(flight.getFlightNumber(),flight);
        }
        return this.flightRepo;
    }

    public void saveToFile() {
        //TODO: Implement Serialization
    }

    @Override
    public FlightDTO getItemById(String id) {
        return null;
    }

    @Override
    public ArrayList<FlightDTO> getAll() {
        return new ArrayList<>(this.flightRepo.values());
    }

}

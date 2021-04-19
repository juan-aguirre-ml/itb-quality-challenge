package com.itbqualitychallenge.bookings.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Repository
public class HotelsReservationRepositoryImple implements HotelsReservationRepository{

    private HashMap<String, HotelDTO> hotelRepo = new HashMap<>();

    public void loadFromFile(String filename) {
        File file = null;
        try {
            file = ResourceUtils.getFile(filename);

        }catch (FileNotFoundException e){
            //TODO: Fix this
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<HotelDTO>> typeRef = new TypeReference<ArrayList<HotelDTO>>() {};
        ArrayList<HotelDTO> db = new ArrayList<>();

        try {
            db = mapper.readValue(file,typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (HotelDTO hotel:db){
            hotelRepo.put(hotel.getHotelCode(),hotel);
        }

    }

    public void saveToFile(String filename) {
        //TODO: Implement Serialization
    }

    @Override
    public HotelDTO getItemById(String id) {
        return this.hotelRepo.getOrDefault(id,null);
    }

    @Override
    public ArrayList<HotelDTO> getAll() {
        return new ArrayList<>(this.hotelRepo.values());
    }




}


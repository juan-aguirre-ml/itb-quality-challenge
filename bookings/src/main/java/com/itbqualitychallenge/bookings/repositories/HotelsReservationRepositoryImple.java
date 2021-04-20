package com.itbqualitychallenge.bookings.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class HotelsReservationRepositoryImple implements HotelsReservationRepository{

    private HashMap<String, HotelDTO> hotelRepo = new HashMap<>();
    private String filename = null;

    public void loadFromFile(String filename) throws FileNotFoundException {
        this.filename = filename;
        File file = null;

        file = ResourceUtils.getFile(filename);
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
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(ResourceUtils.getFile(filename), getAll());


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public HotelDTO getItemById(String id) {
        return this.hotelRepo.getOrDefault(id,null);
    }

    @Override
    public ArrayList<HotelDTO> getAll() {
        return new ArrayList<>(this.hotelRepo.values());
    }

    @Override
    public void setAsReserved(String hotelCode){
        HotelDTO hotel = this.hotelRepo.getOrDefault(hotelCode,null);
        if (hotel != null){
            hotel.setIsReserved(true);
        }
        //TODO Call for a saveToDisk to persist reservations.
        saveToFile(this.filename);
    }



}


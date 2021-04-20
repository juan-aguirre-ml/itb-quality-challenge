package com.itbqualitychallenge.bookings.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.UserDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class UsersRepositoryImple implements UsersRepository{
    private HashMap<String,UserDTO> userRepo = new HashMap<>();

    @Override
    public void loadFromFile(String filename) {
        File file = null;
        try {
            file = ResourceUtils.getFile(filename);

        }catch (FileNotFoundException e){
            //TODO: Fix this
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<UserDTO>> typeRef = new TypeReference<ArrayList<UserDTO>>() {};
        ArrayList<UserDTO> db = null;

        try {
            db = mapper.readValue(file,typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (UserDTO user:db){
            this.userRepo.put(""+user.getDni(),user); //Keep key as string so i can implement a generic base Repository
        }
    }

    @Override
    public void saveToFile(String filename) {
        //TODO: Implement serialization
    }


    @Override
    public UserDTO getItemById(String id) {
        return this.userRepo.getOrDefault(id,null);
    }

    public UserDTO getUserByUsername(String username){
        for (UserDTO user: this.userRepo.values()){
            if (user.getMail().equals(username)){
                return user;
            }
        }
        return null;
    }
}

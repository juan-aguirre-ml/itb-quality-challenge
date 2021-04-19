package com.itbqualitychallenge.bookings.controllers;

import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;
import com.itbqualitychallenge.bookings.services.HotelsReservationService;
import com.itbqualitychallenge.bookings.utils.HotelQueryDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelsController {

    @Autowired
    private HotelsReservationService hotelsReservationService;

    private HotelQueryDTOValidator hotelQueryValidator = new HotelQueryDTOValidator();

    @GetMapping("/hotels")
    public ResponseEntity<ArrayList<HotelDTO>> getAllHotels(@RequestParam(required = false) Map<String,String> queryParams) throws QueryValidationException {
        HotelQueryDTO query = new HotelQueryDTO(queryParams.getOrDefault("dateFrom",null), queryParams.getOrDefault("dateTo",null), queryParams.getOrDefault("destination",null));

        query = hotelQueryValidator.validate(query); //Validacion de user input

        //Aca sigo con la logica del negocio
        return new ResponseEntity<>(hotelsReservationService.getHotelsFromRepo(query), HttpStatus.OK);
    }

}

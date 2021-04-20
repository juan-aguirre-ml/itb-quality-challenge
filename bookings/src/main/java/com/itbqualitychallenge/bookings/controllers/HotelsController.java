package com.itbqualitychallenge.bookings.controllers;

import com.itbqualitychallenge.bookings.dtos.*;
import com.itbqualitychallenge.bookings.exceptions.InvalidDateRangeException;
import com.itbqualitychallenge.bookings.exceptions.InvalidDestinationException;
import com.itbqualitychallenge.bookings.exceptions.ParameterQuantityException;
import com.itbqualitychallenge.bookings.exceptions.QueryValidationException;
import com.itbqualitychallenge.bookings.services.HotelsReservationService;
import com.itbqualitychallenge.bookings.utils.HotelQueryDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelsController {

    @Autowired
    private HotelsReservationService hotelsReservationService;

    @GetMapping("/hotels")
    public ResponseEntity<ArrayList<HotelDTO>> getAllHotels(@RequestParam(required = false) Map<String,String> queryParams) throws FileNotFoundException, InvalidDestinationException, InvalidDateRangeException, ParameterQuantityException {
        HotelQueryDTO query = HotelQueryDTOValidator.validate(queryParams); //Validacion de user input
        return new ResponseEntity<>(hotelsReservationService.getHotelsFromRepo(query), HttpStatus.OK);
    }

    @PostMapping("/booking")
    public ResponseEntity<HotelReservationDTO> createReservation(@RequestBody HotelReservationPayloadDTO payload) throws Exception {
        return new ResponseEntity<>(hotelsReservationService.makeHotelReservation(payload),HttpStatus.OK);
    }

}

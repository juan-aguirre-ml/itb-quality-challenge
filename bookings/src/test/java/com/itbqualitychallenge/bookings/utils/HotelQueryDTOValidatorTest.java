package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.exceptions.InvalidDateRangeException;
import com.itbqualitychallenge.bookings.exceptions.InvalidDestinationException;
import com.itbqualitychallenge.bookings.exceptions.ParameterQuantityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
class HotelQueryDTOValidatorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test behavior with 0 parameters")
    void zeroParameters_ok() {
        HashMap<String,String> params = new HashMap<>();

        assertDoesNotThrow(() -> HotelQueryDTOValidator.validate(params));
    }

    @Test
    @DisplayName("Test behavior with three parameters")
    void threeParameters_ok() {
        HashMap<String,String> params = new HashMap<>();
        params.put("dateFrom","01/01/2021");
        params.put("dateTo","02/01/2021");
        params.put("destination","testLocation");

        assertDoesNotThrow(() -> HotelQueryDTOValidator.validate(params));
    }

    @Test
    @DisplayName("Test behavior with invalid number of parameters")
    void invalidParameters() {
        HashMap<String,String> params = new HashMap<>();
        params.put("1","1");
        params.put("2","2");
        params.put("3","3");
        params.put("4","4");

        assertThrows(ParameterQuantityException.class, () -> HotelQueryDTOValidator.validate(params));
    }

    @Test
    @DisplayName("Test behavior with three parameters but invalid date string")
    void threeParameters_dateParseError() {
        HashMap<String,String> params = new HashMap<>();
        params.put("dateFrom","01/01/2020");
        params.put("dateTo","02/13/2021"); //invalid date
        params.put("destination","3");

        assertThrows(DateTimeParseException.class, () -> HotelQueryDTOValidator.validate(params));
    }

    @Test
    @DisplayName("Test behavior with three parameters but invalid destination")
    void threeParameters_invalidDestination() {
        HashMap<String,String> params = new HashMap<>();
        params.put("dateFrom","01/01/2020");
        params.put("dateTo","02/12/2021");
        params.put("destination","");

        assertThrows(InvalidDestinationException.class, () -> HotelQueryDTOValidator.validate(params));
    }

    @Test
    @DisplayName("Test behavior with three parameters but invalid date range")
    void threeParameters_invalidDateRange() {
        HashMap<String,String> params = new HashMap<>();
        params.put("dateFrom","01/03/2020");
        params.put("dateTo","02/02/2020");
        params.put("destination","testLocation");

        assertThrows(InvalidDateRangeException.class, () -> HotelQueryDTOValidator.validate(params));
    }


}
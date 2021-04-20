package com.itbqualitychallenge.bookings.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import com.itbqualitychallenge.bookings.dtos.HotelQueryDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationPayloadDTO;
import com.itbqualitychallenge.bookings.repositories.HotelsReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebMvc
class HotelsReservationServiceImpleTest {

    private HotelsReservationService hotelService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static ArrayList<HotelDTO> oneHotel;
    private static ArrayList<HotelDTO> manyHotels;
    private static ArrayList<HotelDTO> cataratasHotels;

    @MockBean
    private HotelsReservationRepository hotelsReservationRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws IOException {
        oneHotel = objectMapper.readValue(
                new File("src/test/java/resources/data/oneHotelList.json"),
                new TypeReference<>() {
                });
        manyHotels = objectMapper.readValue(
                new File("src/test/java/resources/data/manyHotelsList.json"),
                new TypeReference<>() {
                });
        cataratasHotels = objectMapper.readValue(
                new File("src/test/java/resources/data/cataratasHotels.json"),
                new TypeReference<>() {
                });
    }

    @Test
    @DisplayName("When no parameters are received, list all hotels.")
    void getHotels_noParam_ok() throws IOException {
        when(hotelsReservationRepository.getAll()).thenReturn(oneHotel);
        hotelService = new HotelsReservationServiceImple(hotelsReservationRepository);
        HotelQueryDTO emptyQuery = new HotelQueryDTO();
        Assertions.assertEquals(oneHotel, hotelService.getHotelsFromRepo(emptyQuery));
    }

    @Test
    @DisplayName("3 correct parameters received.")
    void getHotels_3Param_ok() throws IOException {
        when(hotelsReservationRepository.getAll()).thenReturn(manyHotels);
        hotelService = new HotelsReservationServiceImple(hotelsReservationRepository);
        HotelQueryDTO query =  new HotelQueryDTO();
        query.setDateFrom("10/02/2021");
        query.setDateTo("20/03/2021");
        query.setDestination("Puerto Iguazú");
        Assertions.assertEquals(cataratasHotels, hotelService.getHotelsFromRepo(query));
    }

    @Test
    void getHotelsFromRepo() {


    }

    @Test
    @DisplayName("Correct booking.")
    void makeHotelReservation_ok() throws IOException {

        when(hotelsReservationRepository.getAll()).thenReturn(manyHotels);
        hotelService = new HotelsReservationServiceImple(hotelsReservationRepository);

        //load request from json
        HotelReservationPayloadDTO request = objectMapper.readValue(
                new File("src/test/java/resources/data/booking_ok.json"),
                new TypeReference<>() {
                });
        Assertions.assertDoesNotThrow(() -> hotelService.makeHotelReservation(request));
    }
}
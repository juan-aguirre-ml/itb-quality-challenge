package com.itbqualitychallenge.bookings.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbqualitychallenge.bookings.dtos.HotelDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebMvcTest
public class HotelListIntegrationTests {
    private List<HotelDTO> hotels;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    void setUp() throws IOException {
        hotels = objectMapper.readValue(new File("filepath"), new TypeReference<List<HotelDTO>>() {
        });
    }

    @Test
    void getWithFilter_ok(){
    }

}

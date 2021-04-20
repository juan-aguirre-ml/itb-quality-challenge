package com.itbqualitychallenge.bookings.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itbqualitychallenge.bookings.dtos.HotelBookingDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationPayloadDTO;
import com.itbqualitychallenge.bookings.dtos.PaymentMethodDTO;
import com.itbqualitychallenge.bookings.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class HotelReservationValidatorTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    HotelReservationValidator validator = new HotelReservationValidator();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Validate that the payload is well formed (DTO mapped correctly).")
    void validatePayload_ok() throws IOException {
        HotelReservationPayloadDTO hotel = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_ok.json"),
                new TypeReference<>() {
                });

        assertDoesNotThrow(() -> validator.validatePayload(hotel));

    }
    @Test
    @DisplayName("Malformed payload throws exception when it cant map the booking details.")
    void validatePayload_error() throws IOException {
        HotelReservationPayloadDTO hotel = new HotelReservationPayloadDTO();
        hotel.setUserName("juan@gmail.com");
        hotel.setBooking(null);

        assertThrows(PayloadValidationException.class, () -> validator.validatePayload(hotel));

    }

    @Test
    @DisplayName("Invalid email syntax test.")
    void validatePayload_invalidUsernameException() throws IOException {
        HotelReservationPayloadDTO hotel = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_invalidUsername.json"),
                new TypeReference<>() {
                });
        assertThrows(InvalidEmailException.class, () -> validator.validatePayload(hotel));

    }

    @Test
    @DisplayName("Validate date formatting and basic logic OK.")
    void validateDates_ok() throws IOException {
        HotelBookingDTO booking = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_validDates.json"),
                new TypeReference<>() {
                });
        assertDoesNotThrow(() -> validator.validateDates(booking));

    }

    @Test
    @DisplayName("Validate date formatting and basic logic failure.")
    void validateDates_badFormat() throws IOException {
        HotelBookingDTO booking = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_invalidDates.json"),
                new TypeReference<>() {
                });
        assertThrows(DateTimeParseException.class, () -> validator.validateDates(booking));

    }
    @Test
    @DisplayName("Validate date range.")
    void validateDates_invalidRange() throws IOException {
        HotelBookingDTO booking = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_invalidDateRange.json"),
                new TypeReference<>() {
                });
        assertThrows(InvalidDateRangeException.class, () -> validator.validateDates(booking));

    }



    @Test
    @DisplayName("Pass a valid room size.")
    void validateRoomSize_ok() throws IOException {
        HotelBookingDTO booking = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_roomsize_ok.json"),
                new TypeReference<>() {
                });

        assertDoesNotThrow(() -> validator.validateRoomSize(booking));

    }

    @Test
    @DisplayName("Pass mismatched people amount to the room type.")
    void validateRoomSize_mismatchSize() throws IOException {
        HotelBookingDTO booking = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_mismatchroomsize.json"),
                new TypeReference<>() {
                });
        assertThrows(TooMuchPeopleException.class, () -> validator.validateRoomSize(booking));
    }

    @Test
    @DisplayName("Pass valid payment method.")
    void validatePaymentMethod_ok() throws IOException {
        PaymentMethodDTO paymentMethod = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_payment_ok.json"),
                new TypeReference<>() {
                });

        assertDoesNotThrow(() -> validator.validatePaymentMethod(paymentMethod));
    }

    @Test
    @DisplayName("Pass more than 1 due to a debit card.")
    void validatePaymentMethod_invalidDebitDue() throws IOException {
        PaymentMethodDTO paymentMethod = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_invalidDebitDue.json"),
                new TypeReference<>() {
                });
        assertThrows(DebitDuesException.class, () -> validator.validatePaymentMethod(paymentMethod));

    }

    @Test
    @DisplayName("Pass invalid due amount to a credit card.")
    void validatePaymentMethod_invalidCreditDue() throws IOException {
        PaymentMethodDTO paymentMethod = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_invalidCreditDue.json"),
                new TypeReference<>() {
                });
        assertThrows(CreditDuesException.class, () -> validator.validatePaymentMethod(paymentMethod));

    }
    @Test
    @DisplayName("Pass invalid credit card number.")
    void validatePaymentMethod_invalidCardNumber() throws IOException {
        PaymentMethodDTO paymentMethod = objectMapper.readValue(
                new File("src/test/java/resources/payloads/payload_invalidCardNumber.json"),
                new TypeReference<>() {
                });
        assertThrows(InvalidCardNumberException.class, () -> validator.validatePaymentMethod(paymentMethod));

    }
}
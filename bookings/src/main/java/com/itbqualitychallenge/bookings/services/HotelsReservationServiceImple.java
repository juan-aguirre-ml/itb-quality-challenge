package com.itbqualitychallenge.bookings.services;

import com.itbqualitychallenge.bookings.dtos.*;
import com.itbqualitychallenge.bookings.exceptions.HotelNotFoundException;
import com.itbqualitychallenge.bookings.exceptions.HotelUnavailableException;
import com.itbqualitychallenge.bookings.exceptions.InvalidPaymentException;
import com.itbqualitychallenge.bookings.repositories.HotelsReservationRepository;
import com.itbqualitychallenge.bookings.utils.HotelFilters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class HotelsReservationServiceImple implements HotelsReservationService{

    private static final String AMOUNT = "amount";
    private static final String INTEREST = "interest";
    private static final String TOTAL = "total";

    @Value("${dbHotels}")
    String hotelRepoFilename;
    @Value("${hotel.error.not.found}")
    String hotelErrorNotFound;
    @Value("${hotel.error.unavailable}")
    String hotelErrorUnavailable;
    @Value("${payment.error.default}")
    String paymentErrorDefault;

    private final HotelsReservationRepository hotelRepo;

    public HotelsReservationServiceImple(HotelsReservationRepository hotelRepo) throws FileNotFoundException {
        this.hotelRepo = hotelRepo;
        this.hotelRepo.loadFromFile();
    }


    @Override
    public ArrayList<HotelDTO> getHotelsFromRepo(HotelQueryDTO query) throws FileNotFoundException {
        /*
        This should handle the filtering of the list and return it. The query will be already sanitized by the controller
        so it should be a valid query.
         */

        hotelRepo.loadFromFile();
        ArrayList<HotelDTO> arr = hotelRepo.getAll();
        HotelFilters filters = new HotelFilters();
        arr = filters.filter(arr, query);
        return arr;
    }

    @Override
    public HotelReservationDTO makeHotelReservation(HotelReservationPayloadDTO hotelPayload) throws Exception {
        /*
        This should process everything and change the reserved column to True afterwards.
         */
        hotelRepo.loadFromFile();
        ArrayList<HotelDTO> hotels = hotelRepo.getAll();
        HotelReservationDTO reservation = new HotelReservationDTO();

        String username = hotelPayload.getUserName();
        reservation.setUsername(username);
        HotelBookingDTO booking = hotelPayload.getBooking();
        reservation.setBooking(booking);

        HotelDTO hotel = null;
        for (HotelDTO hot:hotels){
            if (hot.getHotelCode().equals(booking.getHotelCode()))
                hotel = hot;
        }

        if (hotel == null){
            throw new HotelNotFoundException(hotelErrorNotFound);
        }
        if (Boolean.TRUE.equals(hotel.getIsReserved())){
            throw new HotelUnavailableException(hotelErrorUnavailable);
        }

        //Process payment details
        PaymentMethodDTO payment = booking.getPaymentMethod();
        HashMap<String,Float> paymentDetails = getPaymentDetails(payment,Integer.parseInt(hotel.getPricePerNight()));
        reservation.setAmount(paymentDetails.get(AMOUNT));
        reservation.setInterest(paymentDetails.get(INTEREST));
        reservation.setTotal(paymentDetails.get(TOTAL));
        hotelRepo.setAsReserved(hotel.getHotelCode());

        return reservation;

    }

    private HashMap<String, Float> getPaymentDetails(PaymentMethodDTO payment, int price) throws InvalidPaymentException {
        HashMap<String,Float> paymentInfo = new HashMap<>();

        switch (payment.getDues()){
            case 1:
                paymentInfo.put(AMOUNT, (float) price);
                paymentInfo.put(INTEREST, 1F);
                paymentInfo.put(TOTAL,price*1F);
                return paymentInfo;
            case 3:
                paymentInfo.put(AMOUNT, (float) price);
                paymentInfo.put(INTEREST, 1.05F);
                paymentInfo.put(TOTAL,price*1.05F);
                return paymentInfo;
            case 6:
                paymentInfo.put(AMOUNT, (float) price);
                paymentInfo.put(INTEREST, 1.10F);
                paymentInfo.put(TOTAL,price*1.10F);
                return paymentInfo;
            case 12:
                paymentInfo.put(AMOUNT, (float) price);
                paymentInfo.put(INTEREST, 1.15F);
                paymentInfo.put(TOTAL,price*1.15F);
                return paymentInfo;
            default:
                throw new InvalidPaymentException(paymentErrorDefault);
        }
    }

}

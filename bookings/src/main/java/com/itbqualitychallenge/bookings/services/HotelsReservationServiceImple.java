package com.itbqualitychallenge.bookings.services;

import com.itbqualitychallenge.bookings.dtos.*;
import com.itbqualitychallenge.bookings.exceptions.HotelNotFoundException;
import com.itbqualitychallenge.bookings.repositories.HotelsReservationRepository;
import com.itbqualitychallenge.bookings.repositories.UsersRepositoryImple;
import com.itbqualitychallenge.bookings.utils.HotelFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class HotelsReservationServiceImple implements HotelsReservationService{


    private static final String AMOUNT = "amount";
    private static final String INTEREST = "interest";
    private static final String TOTAL = "total";

    @Autowired
    private HotelsReservationRepository hotelRepo;

    @Value("${dbHotels}")
    String hotelRepoFilename;

    @Autowired
    private UsersRepositoryImple usersRepo;


    @Override
    public ArrayList<HotelDTO> getHotelsFromRepo(HotelQueryDTO query) {
        /*
        This shoud handle the filtering of the list and return it. The query will be already sanitized by the controller
        so it should be a valid query.
         */

        hotelRepo.loadFromFile(hotelRepoFilename);
        ArrayList<HotelDTO> arr = hotelRepo.getAll();
        HotelFilters.filter(arr, query);
        return arr;
    }

    @Override
    public HotelReservationDTO makeHotelReservation(HotelReservationPayloadDTO hotelPayload) throws Exception {
        /*
        This should process everything and change the reserved column to True afterwards.
         */

        HotelReservationDTO reservation = new HotelReservationDTO();

        String username = hotelPayload.getUsername();
        reservation.setUsername(username);
        HotelBookingDTO booking = hotelPayload.getBooking();
        reservation.setBooking(booking);

        HotelDTO hotel = (HotelDTO) this.hotelRepo.getItemById(booking.getHotelCode());
        if (hotel == null){
            throw new HotelNotFoundException("${hotel.error.not.found}");
        }

        //Process payment details
        PaymentMethodDTO payment = booking.getPaymentMethod();
        HashMap<String,Float> paymentDetails = getPaymentDetails(payment,Integer.parseInt(hotel.getPricePerNight()));
        reservation.setAmount(paymentDetails.get(AMOUNT));
        reservation.setInterest(paymentDetails.get(INTEREST));
        reservation.setTotal(paymentDetails.get(TOTAL));
        return reservation;

    }

    private HashMap<String, Float> getPaymentDetails(PaymentMethodDTO payment, int price) throws Exception {
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
                //TODO: Make a real exception
                throw new Exception("Something went wrong processing the payment.");
        }
    }

}

package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelBookingDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationPayloadDTO;
import com.itbqualitychallenge.bookings.dtos.PaymentMethodDTO;
import com.itbqualitychallenge.bookings.dtos.UserDTO;
import com.itbqualitychallenge.bookings.exceptions.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class HotelReservationValidator implements PayloadValidator {

    private final Pattern emailPattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

    @Override
    public void validatePayload(Object payload) throws QueryValidationException {
        try {

            HotelReservationPayloadDTO p = (HotelReservationPayloadDTO) payload;
            String username = p.getUsername();
            HotelBookingDTO booking = p.getBooking();
            //Validate email from the payload
            if (!emailPattern.matcher(username).matches()){
                throw new InvalidEmailException("${email.invalid}");
            }
            //Validate date format and range.
            validateDates(booking);

            //People count and room size validation.
            validateRoomSize(booking);

            //Validate peoples emails.
            for (UserDTO user:booking.getPeople()){
                LocalDate.parse(user.getBirthday());
                if (!emailPattern.matcher(user.getEmail()).matches()){
                    throw new InvalidEmailException("${email.invalid}");
                }
            }
            //Validate Payment method and card numbers.
            PaymentMethodDTO paymentMethod = booking.getPaymentMethod();
            validatePaymentMethod(paymentMethod);


        } catch (InvalidDateRangeException | InvalidDestinationException | TooMuchPeopleException | InvalidEmailException | DebitDuesException | InvalidCardNumberException | CreditDuesException e) {
            throw new QueryValidationException(e.getMessage());
        }


    }

    private void validateDates(HotelBookingDTO booking) throws InvalidDateRangeException, InvalidDestinationException {
        //LocalDate Parser will throw an exceptions if date format is invalid
        LocalDate dateFrom = LocalDate.parse(booking.getDateFrom());
        LocalDate dateTo = LocalDate.parse(booking.getDateTo());

        //Validate Date range
        if (dateFrom.isAfter(dateTo) || dateTo.isBefore(dateFrom)) {
            throw new InvalidDateRangeException("${hotel.date.invalid.range}");
        }
        //Validate destination not empty
        if (booking.getDestination().isEmpty()) {
            throw new InvalidDestinationException("${hotel.destination.invalid}");
        }
    }

    public void validateRoomSize(HotelBookingDTO booking) throws TooMuchPeopleException, QueryValidationException {
        int peopleCount = booking.getPeople().size();
        if (peopleCount > 0) {
            switch (booking.getRoomType().toLowerCase()) {
                case "single":
                    if (peopleCount != 1)
                        throw new TooMuchPeopleException("${hotel.people.amount.single}");
                    break;
                case "doble":
                    if (peopleCount > 2 || peopleCount < 1)
                        throw new TooMuchPeopleException("${hotel.people.amount.double}");
                    break;
                case "triple":
                    if (peopleCount > 3 || peopleCount < 1)
                        throw new TooMuchPeopleException("${hotel.people.amount.triple}");
                    break;
                case "multiple":
                    if (peopleCount > 10 || peopleCount < 1)
                        throw new TooMuchPeopleException("${hotel.people.amount.multiple}");
                    break;
                default:
                    throw new QueryValidationException("Rooms can only accommodate 1 to 10 people.");

            }
        }
    }

    public void validatePaymentMethod(PaymentMethodDTO paymentMethodDTO ) throws DebitDuesException, InvalidCardNumberException, CreditDuesException {
        Pattern ccv = Pattern.compile("(\\d{4}[-. ]?){4}|\\d{4}[-. ]?\\d{6}[-. ]?\\d{5}");

        if (ccv.matcher(paymentMethodDTO.getNumber()).matches()) {
            if ("debit".equalsIgnoreCase(paymentMethodDTO.getType())) {
                if (paymentMethodDTO.getDues() != 1) {
                    throw new DebitDuesException("${card.debit.dues}");
            } else {
                if (paymentMethodDTO.getDues() != 3 || paymentMethodDTO.getDues() != 6 || paymentMethodDTO.getDues() != 12)
                    throw new CreditDuesException("${card.credit.dues}");
            }
        }else
            throw new InvalidCardNumberException("${card.number.invalid}");
    }
}
}

package com.itbqualitychallenge.bookings.utils;

import com.itbqualitychallenge.bookings.dtos.HotelBookingDTO;
import com.itbqualitychallenge.bookings.dtos.HotelReservationPayloadDTO;
import com.itbqualitychallenge.bookings.dtos.PaymentMethodDTO;
import com.itbqualitychallenge.bookings.dtos.UserDTO;
import com.itbqualitychallenge.bookings.exceptions.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class HotelReservationValidator implements PayloadValidator {

    @Value("${email.invalid}")
    String emailInvalid;
    @Value("${hotel.validation.date.range.invalid}")
    String invalidDateRange;
    @Value("${hotel.validation.destination.invalid}")
    String destinationInvalid;
    @Value("${hotel.people.amount.single}")
    String singleRoomMessage;
    @Value("${hotel.people.amount.double}")
    String doubleRoomMessage;
    @Value("${hotel.people.amount.triple}")
    String tripleRoomMessage;
    @Value("${hotel.people.amount.multiple}")
    String multipleRoomMessage;
    @Value("${hotel.validation.people.amount.default}")
    String defaultRoomMessage;
    @Value("${card.debit.dues}")
    String cardDebitDues;
    @Value("${card.credit.dues}")
    String cardCreditDues;
    @Value("${card.number.invalid}")
    String cardNumberInvalid;

    private final Pattern emailPattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");

    @Override
    public void validatePayload(Object payload) throws QueryValidationException, InvalidCardNumberException, CreditDuesException, DebitDuesException, InvalidEmailException, TooMuchPeopleException, InvalidDestinationException, InvalidDateRangeException, PayloadValidationException {

        HotelReservationPayloadDTO p = (HotelReservationPayloadDTO) payload;
        String username = p.getUserName();
        HotelBookingDTO booking = p.getBooking();
        if (booking == null){
            throw new PayloadValidationException("The payload was not mapped correctly.");
        }
        //Validate email from the payload
        if (!emailPattern.matcher(username).matches()){
            throw new InvalidEmailException(emailInvalid);
        }
        //Validate date format and range.
        validateDates(booking);

        //People count and room size validation.
        validateRoomSize(booking);

        //Validate peoples emails.
        for (UserDTO user:booking.getPeople()){
            LocalDate.parse(user.getBirthDate(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (!emailPattern.matcher(user.getMail()).matches()){
                throw new InvalidEmailException(emailInvalid);
            }
        }
        //Validate Payment method and card numbers.
        PaymentMethodDTO paymentMethod = booking.getPaymentMethod();
        validatePaymentMethod(paymentMethod);



    }

    public void validateDates(HotelBookingDTO booking) throws DateTimeParseException, InvalidDateRangeException, InvalidDestinationException {
        //LocalDate Parser will throw an exceptions if date format is invalid
        LocalDate dateFrom = LocalDate.parse(booking.getDateFrom(), DateTimeFormatter.ofPattern("d/MM/yyyy"));
        LocalDate dateTo = LocalDate.parse(booking.getDateTo(),DateTimeFormatter.ofPattern("d/MM/yyyy"));

        //Validate Date range
        if (dateFrom.isAfter(dateTo) || dateTo.isBefore(dateFrom)) {
            throw new InvalidDateRangeException(invalidDateRange);
        }
        //Validate destination not empty
        if (booking.getDestination().isEmpty()) {
            throw new InvalidDestinationException(destinationInvalid);
        }
    }

    public void validateRoomSize(HotelBookingDTO booking) throws TooMuchPeopleException {
        int peopleCount = booking.getPeople().size();
        if (peopleCount > 0) {
            switch (booking.getRoomType().toLowerCase()) {
                case "single":
                    if (peopleCount != 1)
                        throw new TooMuchPeopleException(singleRoomMessage);
                    break;
                case "double":
                    if (peopleCount > 2 || peopleCount < 1)
                        throw new TooMuchPeopleException(doubleRoomMessage);
                    break;
                case "triple":
                    if (peopleCount > 3 || peopleCount < 1)
                        throw new TooMuchPeopleException(tripleRoomMessage);
                    break;
                case "multiple":
                    if (peopleCount > 10 || peopleCount < 4)
                        throw new TooMuchPeopleException(multipleRoomMessage);
                    break;
                default:
                    throw new TooMuchPeopleException(defaultRoomMessage);

            }
        }
    }

    public void validatePaymentMethod(PaymentMethodDTO paymentMethodDTO ) throws DebitDuesException, InvalidCardNumberException, CreditDuesException {
        Pattern ccv = Pattern.compile("(\\d{4}[-\\s]?){3}\\d{4}");

        if (ccv.matcher(paymentMethodDTO.getNumber()).matches()) {
            if ("debit".equalsIgnoreCase(paymentMethodDTO.getType())) {
                if (paymentMethodDTO.getDues() != 1)
                    throw new DebitDuesException(cardDebitDues);
            if ("credit".equalsIgnoreCase(paymentMethodDTO.getType())){
                if (paymentMethodDTO.getDues() != 3 || paymentMethodDTO.getDues() != 6 || paymentMethodDTO.getDues() != 12)
                    throw new CreditDuesException(cardCreditDues);
            }
        }else
            throw new InvalidCardNumberException(cardNumberInvalid);
    }
}
}

package com.example.Parking.customValid;

import com.example.Parking.dto.BookingDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomDateCheck implements ConstraintValidator<DateCheck, BookingDTO> {
    @Override
    public boolean isValid(BookingDTO bookingDTO, ConstraintValidatorContext context) {
        if (bookingDTO.getArrivalDate() == null || bookingDTO.getDepartureDate() == null) {
            return true; // Не проверяем, если даты отсутствуют
        }
        return bookingDTO.getArrivalDate().isBefore(bookingDTO.getDepartureDate());
    }
}

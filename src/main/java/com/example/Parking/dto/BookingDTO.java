package com.example.Parking.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class BookingDTO {
    private Long id;

    @NotNull(message = "Arrival date is mandatory")
    @FutureOrPresent(message = "Arrival date must be in the present or future")
    private LocalDateTime arrivalDate;

    @NotNull(message = "Departure date is mandatory")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    private LocalDateTime departureDate;
    private Long userId;
    private Long placeLotId;
}

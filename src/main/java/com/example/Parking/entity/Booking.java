package com.example.Parking.entity;

import com.example.Parking.entity.enumConstants.BookingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;


import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Arrival date is mandatory")
    @FutureOrPresent(message = "Arrival date must be in the present or future")
    private LocalDateTime arrivalDate;
    @NotNull(message = "Departure date is mandatory")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    private LocalDateTime departureDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;
    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;




    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_lot_id", nullable = false)
    @JsonBackReference
    private PlaceLot placeLot;
}

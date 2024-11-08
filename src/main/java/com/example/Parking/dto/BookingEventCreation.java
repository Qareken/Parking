package com.example.Parking.dto;

import com.example.Parking.entity.Booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingEventCreation {
    private Booking booking;
    private String description;
    private BigDecimal changeMoney;
}

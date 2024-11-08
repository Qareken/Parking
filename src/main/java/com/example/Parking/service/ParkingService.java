package com.example.Parking.service;

import com.example.Parking.dto.ParkingPlaceDTO;

import java.math.BigDecimal;

public interface ParkingService {

    ParkingPlaceDTO updatePrice(BigDecimal price);
    BigDecimal getPrice();
}

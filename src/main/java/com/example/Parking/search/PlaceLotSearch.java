package com.example.Parking.search;

import com.example.Parking.customValid.DateCheck;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@DateCheck
public class PlaceLotSearch {

    private LocalDateTime arrivalDate;

    private LocalDateTime departureDate;
}

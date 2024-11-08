package com.example.Parking.dto;

import com.example.Parking.customValid.DateCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@DateCheck
public class PlaceLotDTO {

    private Long id;

    @NotBlank(message = "Name of lot is mandatory")
    @Size(max = 100, message = "Name of lot should not exceed 100 characters")
    private String nameOfLot;


    @NotNull(message = "ParkingPlace ID is mandatory")
    private Long parkingPlaceId;


}
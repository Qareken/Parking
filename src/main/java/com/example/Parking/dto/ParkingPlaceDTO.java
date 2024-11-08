package com.example.Parking.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
@Data
public class ParkingPlaceDTO {
    private Long id;
    @NotBlank(message = "Parking place name is mandatory")
    @Size(max = 100, message = "Parking place name should not exceed 100 characters")
    private String name;
    private Set<Long> placeLotIds;
}

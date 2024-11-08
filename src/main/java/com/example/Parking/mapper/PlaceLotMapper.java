package com.example.Parking.mapper;

import com.example.Parking.dto.PlaceLotDTO;
import com.example.Parking.entity.PlaceLot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaceLotMapper {

    @Mapping(source = "parkingPlace.id", target = "parkingPlaceId")
    PlaceLotDTO toDTO(PlaceLot placeLot);

    @Mapping(source = "parkingPlaceId", target = "parkingPlace.id")
    PlaceLot toEntity(PlaceLotDTO placeLotDTO);
}
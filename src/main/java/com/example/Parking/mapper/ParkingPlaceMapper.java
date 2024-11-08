package com.example.Parking.mapper;

import com.example.Parking.dto.ParkingPlaceDTO;
import com.example.Parking.entity.ParkingPlace;
import com.example.Parking.entity.PlaceLot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingPlaceMapper {

    @Mapping(source = "placeLot", target = "placeLotIds", qualifiedByName = "mapPlaceLotIds")
    ParkingPlaceDTO toDTO(ParkingPlace parkingPlace);

    @Mapping(target = "placeLot", ignore = true) // При необходимости заполните placeLot вручную
    ParkingPlace toEntity(ParkingPlaceDTO parkingPlaceDTO);

    @Named("mapPlaceLotIds")
    default Set<Long> mapPlaceLotIds(Set<PlaceLot> placeLots) {
        return placeLots.stream().map(PlaceLot::getId).collect(Collectors.toSet());
    }
}
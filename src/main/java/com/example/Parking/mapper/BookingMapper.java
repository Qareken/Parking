package com.example.Parking.mapper;

import com.example.Parking.dto.BookingDTO;
import com.example.Parking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "placeLot.id", target = "placeLotId")
    BookingDTO toDTO(Booking booking);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "placeLotId", target = "placeLot.id")
    Booking toEntity(BookingDTO bookingDTO);
    Booking merge(@MappingTarget Booking booking, Booking newBooking);
}
package com.example.Parking.service.impl;

import com.example.Parking.dto.ParkingPlaceDTO;
import com.example.Parking.entity.ParkingPlace;
import com.example.Parking.entity.PlaceLot;
import com.example.Parking.exception.EntityNotFoundException;
import com.example.Parking.mapper.ParkingPlaceMapper;
import com.example.Parking.repository.ParkingRepository;
import com.example.Parking.service.ParkingService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Data
@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;
    private final ParkingPlaceMapper parkingPlaceMapper;
    @Value("${app.parking.name}")
    private String parkingName;
    @PostConstruct
    public void initializeParking(){
        if (parkingRepository.count() == 0) {

            ParkingPlace parkingPlace = new ParkingPlace();
            parkingPlace.setName(parkingName);
            Set<PlaceLot> placeLots = new HashSet<>();
            for (int i = 1; i <= 10; i++) {
                PlaceLot placeLot = new PlaceLot();
                placeLot.setNameOfLot("Lot A" + i);
                placeLot.setParkingPlace(parkingPlace);

                placeLots.add(placeLot);
            }
            parkingPlace.setPrice(BigDecimal.valueOf(30));
            parkingPlace.setPriceTotal(BigDecimal.valueOf(0));
            parkingPlace.setPlaceLot(placeLots);
            parkingRepository.save(parkingPlace);
        }
    }
    @Override
    public ParkingPlaceDTO updatePrice(BigDecimal price) {
        var existPlace =parkingRepository.findByName(parkingName);
        if(existPlace.isPresent()){
           var  place =existPlace.get();
           place.setPrice(price);
           return parkingPlaceMapper.toDTO(parkingRepository.save(place));
        }
        throw  new EntityNotFoundException("Parking Place has not been existed yet");
    }

    @Override
    public BigDecimal getPrice() {
        var existPlace = parkingRepository.findByName(parkingName);
        return existPlace.orElseThrow().getPrice();
    }

}

package com.example.Parking.repository;

import com.example.Parking.entity.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingPlace, Long> {
   Optional<ParkingPlace>  findByName(String name);
}

package com.example.Parking.repository;

import com.example.Parking.entity.PlaceLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceLotRepository  extends JpaRepository<PlaceLot, Long> {

    @Query(value = "SELECT p.* FROM place_lot p " +
            "WHERE NOT EXISTS (" +
            "SELECT 1 FROM un_available_dates u " +
            "WHERE u.place_lot_id = p.id " +
            "AND u.start_date < :departureDate " +
            "AND u.end_date > :arrivalDate)"+
            "LIMIT 1",
            nativeQuery = true)
    Optional<PlaceLot> findAvailablePlaceLot(@Param("arrivalDate") LocalDateTime arrivalDate,
                                                   @Param("departureDate") LocalDateTime departureDate);

}

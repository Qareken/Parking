package com.example.Parking.repository;

import com.example.Parking.entity.BookingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEventRepository extends JpaRepository<BookingEvent, Long> {
}

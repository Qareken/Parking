package com.example.Parking.service;

import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.entity.BookingEvent;
import org.springframework.data.domain.PageRequest;

public interface BookingEventService {
    PageResponseDTO<BookingEvent> findAll(PageRequest pageRequest);
}

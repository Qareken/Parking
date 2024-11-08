package com.example.Parking.controller;


import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.entity.BookingEvent;
import com.example.Parking.service.impl.BookingEventServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking/bookingEvent")
@RequiredArgsConstructor
@Slf4j
public class BookingEventController {
    private final BookingEventServiceImpl bookingEventService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<PageResponseDTO<BookingEvent>> getAllBooking(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingEventService.findAll(pageRequest));
    }
}

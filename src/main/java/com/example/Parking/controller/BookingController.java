package com.example.Parking.controller;

import com.example.Parking.dto.BookingDTO;

import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.dto.PlaceLotDTO;
import com.example.Parking.search.PlaceLotSearch;
import com.example.Parking.service.impl.BookingServiceImpl;
import com.example.Parking.service.impl.PlaceLotServiceImpl;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/parking/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingServiceImpl bookingService;
    private final PlaceLotServiceImpl placeLotService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<PageResponseDTO<BookingDTO>> getAllBooking(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.findAll(pageRequest));
    }
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody @Valid BookingDTO bookingRequestDto){
        return ResponseEntity.ok(bookingService.save(bookingRequestDto));
    }
    @PostMapping("/cancel/{id}")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable  Long id, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(bookingService.freeParking(id));
    }
    @GetMapping("/search")
    public ResponseEntity<PageResponseDTO<PlaceLotDTO>> findAvailable(@RequestBody PlaceLotSearch placeLotSearch, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(placeLotService.findAvailableLot(placeLotSearch,pageRequest));
    }

}

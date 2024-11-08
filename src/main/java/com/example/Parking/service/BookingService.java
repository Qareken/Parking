package com.example.Parking.service;

import com.example.Parking.dto.BookingDTO;
import com.example.Parking.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;

public interface BookingService {
    BookingDTO save(BookingDTO bookingDTO);
    BookingDTO findById(Long id);
//    BookingDTO deleteById(Long id);

    BookingDTO update(BookingDTO bookingDTO, Long id);

    BookingDTO freeParking(Long id);

    PageResponseDTO<BookingDTO> findAll(PageRequest pageRequest);

}

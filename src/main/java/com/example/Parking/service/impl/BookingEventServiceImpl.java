package com.example.Parking.service.impl;

import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.entity.BookingEvent;
import com.example.Parking.mapper.PageResponseMapper;
import com.example.Parking.repository.BookingEventRepository;
import com.example.Parking.service.BookingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingEventServiceImpl implements BookingEventService {
    private final BookingEventRepository bookingEventRepository;
    private final PageResponseMapper pageResponseMapper;

    @Override
    public PageResponseDTO<BookingEvent> findAll(PageRequest pageRequest) {
        return pageResponseMapper.toPageResponseDto(bookingEventRepository.findAll(pageRequest));
    }
}

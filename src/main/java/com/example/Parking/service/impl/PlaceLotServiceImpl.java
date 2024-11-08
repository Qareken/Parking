package com.example.Parking.service.impl;

import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.dto.PlaceLotDTO;
import com.example.Parking.mapper.PageResponseMapper;
import com.example.Parking.mapper.PlaceLotMapper;
import com.example.Parking.repository.PlaceLotRepository;
import com.example.Parking.search.PlaceLotSearch;
import com.example.Parking.service.PlaceLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceLotServiceImpl implements PlaceLotService {
    private final PlaceLotRepository placeLotRepository;
    private final PlaceLotMapper placeLotMapper;
    private final PageResponseMapper pageResponseMapper;
    @Override
    public PageResponseDTO<PlaceLotDTO> findAvailableLot(PlaceLotSearch placeLotSearch, PageRequest pageRequest) {
        var pagePlaceLots = placeLotRepository.findAll(pageRequest);
        var filteredPlaceLots= pagePlaceLots.getContent().stream().filter(placeLot -> placeLot.getUnavailableDates().stream().noneMatch(unavailable ->
                        (unavailable.getStartDate().isBefore(placeLotSearch.getDepartureDate()) && unavailable.getEndDate().isAfter(placeLotSearch.getArrivalDate())))
                ).map(placeLotMapper::toDTO)
                .toList();
        Page<PlaceLotDTO> placeLotDTOPage = new PageImpl<>(filteredPlaceLots, pageRequest, pagePlaceLots.getTotalElements());

        return pageResponseMapper.toPageResponseDto(placeLotDTOPage);
    }
}

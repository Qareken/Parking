package com.example.Parking.service;

import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.dto.PlaceLotDTO;
import com.example.Parking.search.PlaceLotSearch;
import org.springframework.data.domain.PageRequest;

public interface PlaceLotService {
    PageResponseDTO<PlaceLotDTO> findAvailableLot(PlaceLotSearch placeLotSearch, PageRequest pageRequest);
}

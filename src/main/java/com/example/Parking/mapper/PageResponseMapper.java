package com.example.Parking.mapper;

import com.example.Parking.dto.PageResponseDTO;
import org.mapstruct.Mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PageResponseMapper {
    default <T> PageResponseDTO<T> toPageResponseDto(Page<T> page) {
        PageResponseDTO<T> pageResponseDto = new PageResponseDTO<>();
        pageResponseDto.setContent(page.getContent());
        pageResponseDto.setPageNumber(page.getNumber());
        pageResponseDto.setPageSize(page.getSize());
        pageResponseDto.setTotalElements(page.getTotalElements());
        pageResponseDto.setTotalPages(page.getTotalPages());
        return pageResponseDto;
    }
}
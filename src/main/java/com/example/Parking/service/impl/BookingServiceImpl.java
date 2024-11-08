package com.example.Parking.service.impl;

import com.example.Parking.dto.BookingDTO;
import com.example.Parking.dto.BookingEventCreation;
import com.example.Parking.dto.PageResponseDTO;
import com.example.Parking.entity.Booking;
import com.example.Parking.entity.PlaceLot;
import com.example.Parking.entity.UnAvailableDates;
import com.example.Parking.entity.enumConstants.BookingStatus;
import com.example.Parking.exception.EntityNotFoundException;
import com.example.Parking.exception.InsufficientException;
import com.example.Parking.exception.UnAvailableException;
import com.example.Parking.mapper.BookingMapper;
import com.example.Parking.mapper.PageResponseMapper;
import com.example.Parking.repository.BookingRepository;
import com.example.Parking.repository.ParkingRepository;
import com.example.Parking.repository.PlaceLotRepository;
import com.example.Parking.security.AppUserDetails;
import com.example.Parking.security.SecurityService;
import com.example.Parking.service.BookingService;
import com.example.Parking.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final ParkingService parkingService;
    private final PlaceLotRepository placeLotRepository;
    private final UserServiceImpl userService;
    private final BookingMapper bookingMapper;
    private final PageResponseMapper pageResponseMapper;
    private final BookingRepository bookingRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SecurityService securityService;

    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        var pricePerHour = parkingService.getPrice();
        var user = userService.findByPhoneNumber(securityService.getAuthentication().getName());
        long hours = Duration.between(bookingDTO.getArrivalDate(), bookingDTO.getDepartureDate()).toHours();
        BigDecimal hoursBigDecimal = BigDecimal.valueOf(hours);
        BigDecimal totalCost = pricePerHour.multiply(hoursBigDecimal);
        if (user.getBalance().compareTo(totalCost) < 0) {
            throw new InsufficientException("Insufficient funds for the booking. Required: " + totalCost + ", Available: " + user.getBalance());
        }
        var optionalPlaceLot = placeLotRepository.findAvailablePlaceLot(bookingDTO.getArrivalDate(), bookingDTO.getDepartureDate());
        UnAvailableDates date;
        if(optionalPlaceLot.isEmpty()){
            throw new UnAvailableException(MessageFormat.format("Place Lot could not found in the given period {0}, {1}", bookingDTO.getArrivalDate(), bookingDTO.getDepartureDate()));
        }

        date= UnAvailableDates.builder().startDate(bookingDTO.getArrivalDate()).endDate(bookingDTO.getDepartureDate()).placeLot(optionalPlaceLot.get()).build();
        user.setBalance(user.getBalance().subtract(totalCost));
        userService.updateByPrice(user);
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setUser(user);
        var placeLot = optionalPlaceLot.get();
        var dates = placeLot.getUnavailableDates();
        dates.add(date);
        placeLot.setUnavailableDates(dates);
        placeLot=placeLotRepository.save(placeLot);
        booking.setPlaceLot(placeLot);
        booking.setBookingStatus(BookingStatus.ACTIVE);
        booking= bookingRepository.save(booking);
        applicationEventPublisher.publishEvent(new BookingEventCreation(booking, "Booking created", totalCost));

        return bookingMapper.toDTO(booking);
    }

    @Override
    public BookingDTO findById(Long id) {
        return bookingMapper.toDTO(bookingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Booking with this id has not been found!")));
    }

//    @Override
//    public BookingDTO deleteById(Long id) {
//        return null;
//    }

    @Override
    public BookingDTO update(BookingDTO bookingDTO, Long id) {
        var newBooking = bookingMapper.toEntity(bookingDTO);
        var oldBooking = bookingRepository.findById(id);
        return oldBooking.map(booking -> bookingMapper.toDTO(bookingRepository.save(bookingMapper.merge(booking, newBooking)))).orElseThrow(()-> new EntityNotFoundException("Booking with this id not found " +id));
    }

    @Override
    public BookingDTO freeParking(Long id) {
        var bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isEmpty()){
            throw new EntityNotFoundException("Booking with this id not found");
        }else {
          var booking = bookingOptional.get();
          BigDecimal refund = calculateRefund(booking);
          var user = booking.getUser();
          user.setBalance(user.getBalance().add(refund));
          userService.updateByPrice(user);
          updatePlaceLotAvailability(booking.getPlaceLot(), booking.getArrivalDate(), booking.getArrivalDate());
          booking.setBookingStatus(BookingStatus.CANCEL);
          var updatedBooking = bookingRepository.save(booking);
          applicationEventPublisher.publishEvent(new BookingEventCreation(updatedBooking, "Updated booking", refund));
          return bookingMapper.toDTO(updatedBooking);
        }

    }

    @Override
    public PageResponseDTO<BookingDTO> findAll(PageRequest pageRequest) {
        return pageResponseMapper.toPageResponseDto(bookingRepository.findAll(pageRequest).map(bookingMapper::toDTO));
    }

    private void updatePlaceLotAvailability(PlaceLot placeLot, LocalDateTime arrivalDate, LocalDateTime departureDate) {
        placeLot.getUnavailableDates().removeIf(dateRange ->
                dateRange.getStartDate().isBefore(departureDate) && dateRange.getEndDate().isAfter(arrivalDate)
        );
        UnAvailableDates usedDateRange = new UnAvailableDates();
        usedDateRange.setStartDate(arrivalDate);
        usedDateRange.setEndDate(LocalDateTime.now());
        usedDateRange.setPlaceLot(placeLot);
        placeLot.getUnavailableDates().add(usedDateRange);
        placeLotRepository.save(placeLot);
    }
     private BigDecimal  calculateRefund(Booking booking) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime arrivalDate = booking.getArrivalDate();
        LocalDateTime departureDate = booking.getDepartureDate();

        long daysLeft = ChronoUnit.DAYS.between(now, departureDate);
        BigDecimal refundPercentage;
        if (daysLeft >= 3) {
            refundPercentage = BigDecimal.valueOf(0.20);
        } else if (daysLeft == 2) {
            refundPercentage = BigDecimal.valueOf(0.30);
        } else if (daysLeft == 1) {
            refundPercentage = BigDecimal.valueOf(0.50);
        } else {
            refundPercentage = BigDecimal.ZERO;
        }

        long hoursUsed = Duration.between(arrivalDate, now).toHours();
        long totalHours = Duration.between(arrivalDate, departureDate).toHours();
        long unusedHours = Math.max(totalHours - hoursUsed, 0);

        BigDecimal pricePerHour = parkingService.getPrice();
        BigDecimal unusedCost = pricePerHour.multiply(BigDecimal.valueOf(unusedHours));


        return unusedCost.multiply(refundPercentage);
    }
}

package com.example.Parking.eventListener;

import com.example.Parking.dto.BookingEventCreation;
import com.example.Parking.entity.Booking;
import com.example.Parking.entity.BookingEvent;
import com.example.Parking.repository.BookingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BookingEventListener {


    private final BookingEventRepository bookingEventRepository;

    @EventListener
    public void handleBookingCreatedEvent(BookingEventCreation bookingEventCreation) {
        // Создаем запись в BookingEvent
        BookingEvent bookingEvent = new BookingEvent();
        bookingEvent.setBookingId(bookingEventCreation.getBooking().getId());
        bookingEvent.setDescription(bookingEventCreation.getDescription());
        bookingEvent.setEventTime(LocalDateTime.now());
        bookingEvent.setChangeMoney(bookingEventCreation.getChangeMoney());
        bookingEventRepository.save(bookingEvent);
    }
}

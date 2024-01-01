package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BookingService {
    ResponseEntity<List<BookingTrackerDto>> saveBookingTracker(BookingTrackerConverterDto bookingTrackerConverterDto);

    BookingTrackerDto updateBooking(String id);
}

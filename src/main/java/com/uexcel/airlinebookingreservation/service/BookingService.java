package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import com.uexcel.airlinebookingreservation.dto.BookingUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface BookingService {
    ResponseEntity<List<BookingTrackerDto>> saveBookingTracker(BookingTrackerConverterDto bookingTrackerConverterDto);

    BookingTrackerDto updateBookingStatus(String id);

    BookingTrackerDto updateBooking(BookingUpdateDto bookingUpdateDto);
}

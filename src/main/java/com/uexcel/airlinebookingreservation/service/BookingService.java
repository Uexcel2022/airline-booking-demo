package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingDto;
import com.uexcel.airlinebookingreservation.dto.BookingUpdateDto;
import com.uexcel.airlinebookingreservation.entity.FlightSchedule;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BookingService {
    ResponseEntity<List<BookingDto>> saveBookingTracker(BookingConverterDto bookingConverterDto);

    BookingDto updateBookingStatus(String id);

    BookingDto updateBooking(BookingUpdateDto bookingUpdateDto);

}

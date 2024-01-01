package com.uexcel.airlinebookingreservation.controller;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import com.uexcel.airlinebookingreservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/booking-tracker")
public class BookingTrackerController {
    private final BookingService bookingService;

    public BookingTrackerController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<List<BookingTrackerDto>> saveBooking(
            @RequestBody BookingTrackerConverterDto bookingTrackerConverterDto){
        return bookingService.saveBookingTracker(bookingTrackerConverterDto);
    }
}

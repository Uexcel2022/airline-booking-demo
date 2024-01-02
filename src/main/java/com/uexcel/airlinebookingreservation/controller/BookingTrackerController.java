package com.uexcel.airlinebookingreservation.controller;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import com.uexcel.airlinebookingreservation.dto.BookingUpdateDto;
import com.uexcel.airlinebookingreservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    @PutMapping("/update-status")
    public ResponseEntity<BookingTrackerDto> updateBookingStatus(@RequestBody() Map<String, String> id){
      return ResponseEntity.ok().body(bookingService.updateBookingStatus(id.get("id")));

    }

    @PutMapping("/update")
    public ResponseEntity<BookingTrackerDto> updateBooking(@RequestBody() BookingUpdateDto bookingUpdateDto){
        return ResponseEntity.ok().body(bookingService.updateBooking(bookingUpdateDto));

    }
}

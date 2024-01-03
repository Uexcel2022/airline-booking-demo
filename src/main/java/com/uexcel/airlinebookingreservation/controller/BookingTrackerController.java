package com.uexcel.airlinebookingreservation.controller;


import com.uexcel.airlinebookingreservation.dto.*;
import com.uexcel.airlinebookingreservation.service.BookingDisplayService;
import com.uexcel.airlinebookingreservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/booking")
public class BookingTrackerController {
    private final BookingService bookingService;
    private final BookingDisplayService bookingDisplayService;

    public BookingTrackerController(BookingService bookingService,
                                    BookingDisplayService bookingDisplayService) {
        this.bookingService = bookingService;
        this.bookingDisplayService = bookingDisplayService;
    }

    @PostMapping
    public ResponseEntity<List<BookingDto>> saveBooking(
            @RequestBody BookingConverterDto bookingConverterDto){
        return bookingService.saveBookingTracker(bookingConverterDto);
    }

    @PutMapping("/update-status")
    public ResponseEntity<BookingDto> updateBookingStatus(@RequestBody() Map<String, String> id){
      return ResponseEntity.ok().body(bookingService.updateBookingStatus(id.get("id")));

    }

    @PutMapping("/update")
    public ResponseEntity<BookingDto> updateBooking(@RequestBody() BookingUpdateDto bookingUpdateDto){
        return ResponseEntity.ok().body(bookingService.updateBooking(bookingUpdateDto));

    }

    @GetMapping("/booking")
    public List<AvailableSeats> getAvailableSeat(
            @RequestParam("aircraft") String aircraftNumber,
            @RequestParam("departure") String departureTime,
            @RequestParam("date") LocalDate date
            ){
        return bookingDisplayService.findBooking(aircraftNumber, departureTime, date);
    }

    @GetMapping("/flight_schedule")
    public List<FlightScheduleDto> flightSchedule(@RequestParam("date") LocalDate date){
        return bookingDisplayService.getFlightSchedule(date);
    }

}

package com.uexcel.airlinebookingreservation.controller;


import com.uexcel.airlinebookingreservation.dto.*;
import com.uexcel.airlinebookingreservation.entity.Seat;
import com.uexcel.airlinebookingreservation.service.BookingDisplayService;
import com.uexcel.airlinebookingreservation.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/booking")
public class BookingTrackerController {
    private final BookingService bookingService;
    private final BookingDisplayService bookingDisplayService;

    public BookingTrackerController(BookingService bookingService,
                                    BookingDisplayService bookingDisplayService) {
        this.bookingService = bookingService;
        this.bookingDisplayService = bookingDisplayService;
    }

    @PostMapping("/book")
    public ResponseEntity<List<BookingDto>> saveBooking(
            @RequestBody BookingConverterDto bookingConverterDto){
        return bookingService.saveBooking(bookingConverterDto);
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
    public String flightSchedule(@RequestParam("date") LocalDate date, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<FlightScheduleDto> flightSchedule = bookingDisplayService.getFlightSchedule(date);
        session.setAttribute("flightSchedule", flightSchedule);
        model.addAttribute("flights",flightSchedule);
         return "available_flights";
    }

    @PostMapping("/check-booking")
    public ResponseEntity<BookingDto> checkBooking(@RequestBody Map<String, String> bookingId){
      BookingDto bookingDto =  bookingService.checkBooking(bookingId.get("id"));
      return ResponseEntity.ok().body(bookingDto);
    }

    @GetMapping("/book")
    public String booking(@RequestParam("flight_id") int id, Model model, HttpServletRequest request){

        ArrayList<FlightScheduleDto> flight = new ArrayList<>();
        HttpSession session = request.getSession();
        List<FlightScheduleDto> flightSchedule = (List<FlightScheduleDto>) session.getAttribute("flightSchedule");

        for(FlightScheduleDto n: flightSchedule){
            if(n.getId() == id){
                flight.add(n);
                break;
            }
        }

        List<AvailableSeats> availableSeats = bookingDisplayService.findBooking(
                flight.get(0).getAircraftNumber(),flight.get(0).getDepartureTime(),flight.get(0).getDate());

        model.addAttribute("flights",flight);
        model.addAttribute("seats", availableSeats);
        model.addAttribute("flight_id", id);

        return "available_seats";
    }

}

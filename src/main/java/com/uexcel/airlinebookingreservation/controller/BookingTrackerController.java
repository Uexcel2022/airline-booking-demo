package com.uexcel.airlinebookingreservation.controller;


import com.uexcel.airlinebookingreservation.dto.DateDto;
import com.uexcel.airlinebookingreservation.dto.*;
import com.uexcel.airlinebookingreservation.service.BookingDisplayService;
import com.uexcel.airlinebookingreservation.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping
    public String getFlightSchedule(@ModelAttribute("date") DateDto date){
        return "booking-page";
    }

    @GetMapping(value = {"/check-booking","/update"})
    public String checkBooking(@ModelAttribute("bookingId") BookingIdDto bookingId, HttpServletRequest request){
        if(request.getServletPath().equals("/booking/check-booking")) {
            return "check-booking";
        }
        return "update-booking";
    }

    @PostMapping("/book")
    public String saveBooking(
            @ModelAttribute("bookingConverterDto") BookingConverterDto bookingConverterDto,
            HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        FlightScheduleDto flightSchedule = (FlightScheduleDto) session.getAttribute("flight");
        bookingConverterDto.setAircraftNumber1(flightSchedule.getAircraftNumber());
        bookingConverterDto.setDate1(flightSchedule.getDate());
        bookingConverterDto.setDepartureTime1(flightSchedule.getDepartureTime());
        bookingConverterDto.setArrivalTime1(flightSchedule.getArrivalTime());
        bookingConverterDto.setFrom1(flightSchedule.getOrigin());
        bookingConverterDto.setDestination1(flightSchedule.getDestination());
        bookingConverterDto.setPrice(flightSchedule.getPrice());
       List<BookingDto> bookingDto = bookingService.saveBooking(bookingConverterDto);
       model.addAttribute("receipt",bookingDto);
        return "bookingdetails";
    }

    @PutMapping("/update-status")
    public ResponseEntity<BookingDto> updateBookingStatus(@RequestBody() Map<String, String> id){
      return ResponseEntity.ok().body(bookingService.updateBookingStatus(id.get("id")));

    }

    @PostMapping("/save-update")
    public String updateBooking(@ModelAttribute("bookingId") BookingDto bookingDto, Model model ){
        bookingService.updateBooking(bookingDto);
        BookingDto bookingDto1 = bookingService.checkBooking(bookingDto.getBookingId());
                model.addAttribute("receipt",bookingDto1);
        return "bookingdetails";
    }

    @GetMapping("/booking")
    public List<AvailableSeats> getAvailableSeat(
            @RequestParam("aircraft") String aircraftNumber,
            @RequestParam("departure") String departureTime,
            @RequestParam("date") LocalDate date
            ){
        return bookingDisplayService.findBooking(aircraftNumber, departureTime, date);
    }

    @PostMapping("/flight_schedule")
    public String flightSchedule(@ModelAttribute("date") DateDto date, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<FlightScheduleDto> flightSchedule = bookingDisplayService.getFlightSchedule(date.getDate());
        session.setAttribute("flightSchedule", flightSchedule);
        model.addAttribute("flights",flightSchedule);
         return "available_flights";
    }

    @PostMapping(value = {"/check-booking", "/update"})
    public String checkBooking(@ModelAttribute("bookingId")
                                   BookingIdDto bookingId, Model model, HttpServletRequest request){
      BookingDto bookingDto =  bookingService.checkBooking(bookingId.getBookingId());
        model.addAttribute("receipt",bookingDto);
        List<AvailableSeats> availableSeats = bookingDisplayService.findBooking(
                bookingDto.getAircraftNumber(),bookingDto.getDepartureTime(),bookingDto.getDate());
        model.addAttribute("seats", availableSeats);

        if(request.getServletPath().equals("/booking/check-booking")){
            return "bookingdetails";
        }

        if(request.getServletPath().equals("/booking/update")){
            return "update-page";
        }

        return null;
    }

    @GetMapping("/book")
    public String booking(@ModelAttribute("dto") BookingConverterDto dto,
            @RequestParam("flight_id") int id, Model model, HttpServletRequest request){

        FlightScheduleDto flight = null;
        HttpSession session = request.getSession();
        List<FlightScheduleDto> flightSchedule = (List<FlightScheduleDto>) session.getAttribute("flightSchedule");

        for(FlightScheduleDto n: flightSchedule){
            if(n.getId() == id){
                flight = new FlightScheduleDto();
                flight.setAircraftNumber(n.getAircraftNumber());
                flight.setDate(n.getDate());
                flight.setOrigin(n.getOrigin());
                flight.setArrivalTime(n.getArrivalTime());
                flight.setDepartureTime(n.getDepartureTime());
                flight.setDestination(n.getDestination());
                flight.setPrice(n.getPrice());
                break;
            }
        }

        assert flight != null;
        List<AvailableSeats> availableSeats = bookingDisplayService.findBooking(
                flight.getAircraftNumber(),flight.getDepartureTime(),flight.getDate());

        session.setAttribute("flight",flight);
        model.addAttribute("flights",flight);
        model.addAttribute("seats", availableSeats);
        model.addAttribute("flight_id", id);

        return "available_seats";
    }

}

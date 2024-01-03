package com.uexcel.airlinebookingreservation.service;

import com.uexcel.airlinebookingreservation.dto.AvailableSeats;
import com.uexcel.airlinebookingreservation.dto.FlightScheduleDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingDisplayService {
    List<AvailableSeats> findBooking(String aircraftNumber, String departureTime, LocalDate date);

    List<FlightScheduleDto> getFlightSchedule(LocalDate dayOfWeek);
}

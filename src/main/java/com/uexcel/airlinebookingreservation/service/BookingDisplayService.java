package com.uexcel.airlinebookingreservation.service;

import com.uexcel.airlinebookingreservation.dto.SeatDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingDisplayService {
    List<SeatDto> findBooking(String aircraftNumber, String departureTime, LocalDate date);
}

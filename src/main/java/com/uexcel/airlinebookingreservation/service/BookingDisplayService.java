package com.uexcel.airlinebookingreservation.service;

import java.time.LocalDate;
import java.util.List;

public interface BookingDisplayService {
    List<Integer> findBooking(String aircraftNumber, String departureTime, LocalDate date);
}

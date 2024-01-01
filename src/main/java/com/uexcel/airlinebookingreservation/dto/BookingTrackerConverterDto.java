package com.uexcel.airlinebookingreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTrackerConverterDto {
    private String aircraftNumber1;
    private int seatNumber1;
    private String departureTime1;
    private LocalDate date1;
    private String aircraftNumber2;
    private int seatNumber2;
    private String departureTime2;
    private LocalDate date2;
}

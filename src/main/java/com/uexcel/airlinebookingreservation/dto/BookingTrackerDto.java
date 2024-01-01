package com.uexcel.airlinebookingreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTrackerDto {
    private String id;
    private String aircraftNumber;
    private int seatNumber;
    private String departureTime;
    private String date;
}

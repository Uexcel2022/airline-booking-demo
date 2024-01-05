package com.uexcel.airlinebookingreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String firstName;
    private String lastName;
    private String nextOfKingNumber;
    private String address;
    private String bookingId;
    private String aircraftNumber;
    private int seatNumber;
    private String departureTime;
    private LocalDate date;
    private String weekDay;
    private String origin;
    private String destination;
    private String arrivalTime;
    private Double price;
    private String email;
    private String status;
}

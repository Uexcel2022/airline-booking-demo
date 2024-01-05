package com.uexcel.airlinebookingreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateDto {
    private String bookingId;
    private String firstName;
    private String lastName;
    private String nextOfKingNumber;
    private String address;
    private String aircraftNumber;
    private int seatNumber;
    private String departureTime;
    private LocalDate date;
    private String origin;
    private String destination;
    private String arrivalTime;
    private Double price;
    private String email;
    private String status;
}

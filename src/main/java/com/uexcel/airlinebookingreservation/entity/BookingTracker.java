package com.uexcel.airlinebookingreservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking_tracker")
public class BookingTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookingId;
    private String aircraftNumber;
    private int seatNumber;
    private String departureTime;
    private int year;
    private int dayOfYear;
    private String status;

    public BookingTracker(String bookingId, String aircraftNumber, int seatNumber, String departureTime, int year, int dayOfYear, String status) {
        this.bookingId = bookingId;
        this.aircraftNumber = aircraftNumber;
        this.seatNumber = seatNumber;
        this.departureTime = departureTime;
        this.year = year;
        this.dayOfYear = dayOfYear;
        this.status = status;
    }
}

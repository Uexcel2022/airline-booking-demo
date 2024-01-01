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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id = UUID.randomUUID().toString();
    private String aircraftNumber;
    private int seatNumber;
    private String departureTime;
    private int year;
    private int dayOfYear;}

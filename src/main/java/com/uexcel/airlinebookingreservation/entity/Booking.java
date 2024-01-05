package com.uexcel.airlinebookingreservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="booking")
public class Booking {
    @Id
    private String bookingId = UUID.randomUUID().toString();
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

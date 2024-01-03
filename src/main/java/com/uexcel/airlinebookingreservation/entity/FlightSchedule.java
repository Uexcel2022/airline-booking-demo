package com.uexcel.airlinebookingreservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="flight_schedule")
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aircraftNumber;
//    private LocalDate date;
    private String weekDay;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Double price;

}

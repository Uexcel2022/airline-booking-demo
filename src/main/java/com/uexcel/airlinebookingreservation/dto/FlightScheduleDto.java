package com.uexcel.airlinebookingreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightScheduleDto {
    private Long id;
    private String aircraftNumber;
    private LocalDate date;
    private String weekDay;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Double price;
}

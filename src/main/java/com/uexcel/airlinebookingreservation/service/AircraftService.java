package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.AircraftDto;


public interface AircraftService {
    AircraftDto saveAircraft(AircraftDto aircraftDto);

    AircraftDto getAircraftByNumber(String aircraftNumber);
}

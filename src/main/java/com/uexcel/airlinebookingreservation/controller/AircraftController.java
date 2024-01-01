package com.uexcel.airlinebookingreservation.controller;


import com.uexcel.airlinebookingreservation.dto.AircraftDto;
import com.uexcel.airlinebookingreservation.service.AircraftService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/aircraft")
public class AircraftController {
    private  final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping
    public AircraftDto saveAircraft(@RequestBody AircraftDto aircraftDto){
        return aircraftService.saveAircraft(aircraftDto);
    }

    @GetMapping("/{aircraft-no}")
    public AircraftDto getAircraftByNumber(
            @PathVariable("aircraft-no")String aircraftNumber){
        return aircraftService.getAircraftByNumber(aircraftNumber);
    }

}


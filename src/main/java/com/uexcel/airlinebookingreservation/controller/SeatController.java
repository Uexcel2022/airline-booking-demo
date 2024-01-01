//package com.uexcel.airlinebookingreservation.controller;
//
//import com.uexcel.airlinebookingreservation.service.SeatService;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/seat")
//public class SeatController {
//    private final SeatService seatService;
//
//    public SeatController(SeatService seatService) {
//        this.seatService = seatService;
//    }
//
//    @PostMapping("/{seat_no}")
//    public SeatDto saveSeat(@RequestBody SeatDto seatDtoMono,
//                                  @PathVariable("seat_no") String seatNumber){
//        return seatService.saveSeat(seatDtoMono, seatNumber);
//    }
//}

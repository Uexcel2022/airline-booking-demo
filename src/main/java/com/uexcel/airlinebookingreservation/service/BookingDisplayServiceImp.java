package com.uexcel.airlinebookingreservation.service;

import com.uexcel.airlinebookingreservation.entity.Seat;
import com.uexcel.airlinebookingreservation.repository.BookingRepository;
import com.uexcel.airlinebookingreservation.repository.BookingTrackerRepository;
import com.uexcel.airlinebookingreservation.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingDisplayServiceImp implements  BookingDisplayService{
   private final BookingRepository bookingRepository;
   private final BookingTrackerRepository bookingTrackerRepository;

   private  final SeatRepository seatRepository;

    public BookingDisplayServiceImp(BookingRepository bookingRepository,
                                    BookingTrackerRepository bookingTrackerRepository,
                                    SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingTrackerRepository = bookingTrackerRepository;
        this.seatRepository = seatRepository;
    }


    @Override
    public List<Integer> findBooking(String aircraftNumber, String departureTime, LocalDate date) {

        ArrayList<Integer> availableSeats =  new ArrayList<>();
        List<Integer> bookingTrackerList =
                bookingTrackerRepository.booking(
                        aircraftNumber,date.getYear(),date.getDayOfYear(), departureTime);

        List<Integer> seatList = seatRepository.AD473N6();

        for(Integer n : seatList){
            if(!bookingTrackerList.contains(n)){
                availableSeats.add(n);
            }
        }





        return availableSeats;
    }
}

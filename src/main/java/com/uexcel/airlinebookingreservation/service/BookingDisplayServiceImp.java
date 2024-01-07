package com.uexcel.airlinebookingreservation.service;

import com.uexcel.airlinebookingreservation.dto.AvailableSeats;
import com.uexcel.airlinebookingreservation.dto.FlightScheduleDto;
import com.uexcel.airlinebookingreservation.entity.FlightSchedule;
import com.uexcel.airlinebookingreservation.exception.IncorrectDataException;
import com.uexcel.airlinebookingreservation.repository.BookingTrackerRepository;
import com.uexcel.airlinebookingreservation.repository.FlightScheduleRepository;
import com.uexcel.airlinebookingreservation.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingDisplayServiceImp implements  BookingDisplayService{
    private final FlightScheduleRepository flightScheduleRepository;
   private final BookingTrackerRepository bookingTrackerRepository;

   private  final SeatRepository seatRepository;

    public BookingDisplayServiceImp(
            FlightScheduleRepository flightScheduleRepository,
            BookingTrackerRepository bookingTrackerRepository,
            SeatRepository seatRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.bookingTrackerRepository = bookingTrackerRepository;
        this.seatRepository = seatRepository;
    }


    @Override
    public List<AvailableSeats> findBooking(String aircraftNumber, String departureTime, LocalDate date) {

        ArrayList<AvailableSeats> availableSeats =  new ArrayList<>();
        List<Integer> bookingTrackerList =
                bookingTrackerRepository.booking(
                        aircraftNumber,date.getYear(),date.getDayOfYear(), departureTime);

        List<Integer> seatList = null;

        if(aircraftNumber.equals("AD473N6")) {
            seatList = seatRepository.AD473N6();
        }

        if(aircraftNumber.equals("DC407N1")) {
            seatList = seatRepository.DC407N1();
        }

        assert seatList != null;
        for(Integer n : seatList){
            if(n!=null && !bookingTrackerList.contains(n)){
                availableSeats.add(new AvailableSeats(n));
            }
        }

        return availableSeats;
    }

    @Override
    public List<FlightScheduleDto> getFlightSchedule(LocalDate date) {
        if(date.getYear() < LocalDate.now().getYear()){
            throw new IncorrectDataException("Past date is invalid");
        }

        if(date.getYear() == LocalDate.now().getYear() && date.getDayOfYear() < LocalDate.now().getDayOfYear()){
            throw new IncorrectDataException("Past date is invalid");
        }
        List<FlightScheduleDto> flightScheduleDtoArrayList = new ArrayList<>();

        List<FlightSchedule>  flightSchedules = flightScheduleRepository.findByWeekDay(date.getDayOfWeek().name());

            for(FlightSchedule n : flightSchedules) {
                FlightScheduleDto flightScheduleDto = new FlightScheduleDto();
                flightScheduleDto.setId(n.getId());
                flightScheduleDto.setAircraftNumber(n.getAircraftNumber());
                flightScheduleDto.setDate(date);
                flightScheduleDto.setOrigin(n.getOrigin());
                flightScheduleDto.setDepartureTime(n.getDepartureTime());
                flightScheduleDto.setDestination(n.getDestination());
                flightScheduleDto.setArrivalTime(n.getArrivalTime());
                flightScheduleDto.setWeekDay(n.getWeekDay());
                flightScheduleDto.setPrice(n.getPrice());
                if(date.getDayOfYear() - LocalDate.now().getDayOfYear() >= 21){
                 double discountedPrice =  n.getPrice() -  n.getPrice() * 15/100;
                 flightScheduleDto.setPrice(discountedPrice);
                }
                flightScheduleDtoArrayList.add(flightScheduleDto);
            }

        return flightScheduleDtoArrayList;
    }
}

package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import com.uexcel.airlinebookingreservation.entity.BookingTracker;
import com.uexcel.airlinebookingreservation.repository.BookingTrackerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImp implements BookingService {
    private final BookingTrackerRepository bookingTrackerRepository;

    public BookingServiceImp(BookingTrackerRepository bookingTrackerRepository) {
        this.bookingTrackerRepository = bookingTrackerRepository;
    }

    @Override
    public ResponseEntity<List<BookingTrackerDto>> saveBookingTracker(BookingTrackerConverterDto bookingTrackerConverterDto) {

        BookingTracker bookingTracker1 = convertToEntityToTicket(bookingTrackerConverterDto);

        if(bookingTrackerConverterDto.getAircraftNumber2() != null && bookingTrackerConverterDto.getDate2() != null){

            validateBooking1(bookingTracker1,bookingTrackerConverterDto, bookingTrackerRepository);

            BookingTracker bookingTracker2 = convertToEntityReturnTicket(bookingTrackerConverterDto);

            validateBooking2(bookingTracker2,bookingTrackerConverterDto, bookingTrackerRepository);

            return ResponseEntity.ok().body(List.of(convertToDto(bookingTracker1 , bookingTrackerRepository),
                   convertToDto(bookingTracker2, bookingTrackerRepository)));
        }


        validateBooking1(bookingTracker1,bookingTrackerConverterDto, bookingTrackerRepository);

        return ResponseEntity.ok().body(List.of(convertToDto(bookingTracker1, bookingTrackerRepository)));
    }

    @Override
    public BookingTrackerDto updateBooking(String id) {

        Optional<BookingTracker> bookingTracker =
                bookingTrackerRepository.findById(id);
        if(bookingTracker.isPresent()){
            BookingTracker tr = bookingTracker.get();
            tr.setStatus("used");
          return convertToDto(tr,bookingTrackerRepository);
        }
        throw new RuntimeException("Booking not found");
    }


    private static BookingTracker convertToEntityToTicket(BookingTrackerConverterDto bookingTrackerConverterDto) {

        int year1 = bookingTrackerConverterDto.getDate1().getYear();
        int dayOfYear1 = bookingTrackerConverterDto.getDate1().getDayOfYear();

        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber1());
        bookingTracker.setDepartureTime(bookingTrackerConverterDto.getDepartureTime1());
        bookingTracker.setDayOfYear(dayOfYear1);
        bookingTracker.setSeatNumber(bookingTrackerConverterDto.getSeatNumber1());
        bookingTracker.setYear(year1);
        bookingTracker.setStatus("unused");

        return bookingTracker;
    }

    private static BookingTracker convertToEntityReturnTicket(BookingTrackerConverterDto bookingTrackerConverterDto) {

        int year2 = bookingTrackerConverterDto.getDate2().getYear();
        int dayOfYear2 = bookingTrackerConverterDto.getDate2().getDayOfYear();
        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber2());
        bookingTracker.setDepartureTime(bookingTrackerConverterDto.getDepartureTime2());
        bookingTracker.setDayOfYear(dayOfYear2);
        bookingTracker.setSeatNumber(bookingTrackerConverterDto.getSeatNumber2());
        bookingTracker.setYear(year2);
        bookingTracker.setStatus("unused");

        return bookingTracker;

    }


    public static  BookingTrackerDto convertToDto(BookingTracker bookingTracker,
                                                  BookingTrackerRepository bookingTrackerRepository){
        BookingTrackerDto bookingTrackerDto = new BookingTrackerDto();
        bookingTrackerDto.setAircraftNumber(bookingTracker.getAircraftNumber());
        bookingTrackerDto.setSeatNumber(bookingTracker.getSeatNumber());
        bookingTrackerDto.setDate(
                String.valueOf(LocalDate.ofYearDay(bookingTracker.getYear(),bookingTracker.getDayOfYear())));
        bookingTrackerDto.setDepartureTime(bookingTracker.getDepartureTime());
        bookingTrackerRepository.save(bookingTracker);
        bookingTrackerDto.setId(bookingTracker.getId());
        return  bookingTrackerDto;
    }

    public  static  void validateBooking1(
            BookingTracker bookingTracker, BookingTrackerConverterDto bookingTrackerConverterDto,
    BookingTrackerRepository bookingTrackerRepository) {
            BookingTracker booking = bookingTrackerRepository.findByGo(
                    bookingTracker.getAircraftNumber(), bookingTracker.getSeatNumber(),
                    bookingTracker.getYear(), bookingTracker.getDayOfYear(),
                    bookingTracker.getDepartureTime());

            if (booking != null) {
                throw new RuntimeException("The seat on " + bookingTrackerConverterDto.getAircraftNumber1()
                        + " has been booked on this date " + bookingTrackerConverterDto.getDate1());
            }
    }

    public  static  void validateBooking2(
            BookingTracker bookingTracker, BookingTrackerConverterDto bookingTrackerConverterDto,
            BookingTrackerRepository bookingTrackerRepository) {
        BookingTracker booking = bookingTrackerRepository.findByGo(
                bookingTracker.getAircraftNumber(), bookingTracker.getSeatNumber(),
                bookingTracker.getYear(), bookingTracker.getDayOfYear(),
                bookingTracker.getDepartureTime());

        if (booking != null) {
            throw new RuntimeException("The seat on " + bookingTrackerConverterDto.getAircraftNumber2()
                    + " has been booked on this date " + bookingTrackerConverterDto.getDate2());
        }
    }
}
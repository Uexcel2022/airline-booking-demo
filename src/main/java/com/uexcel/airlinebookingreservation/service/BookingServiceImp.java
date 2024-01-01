package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import com.uexcel.airlinebookingreservation.entity.BookingTracker;
import com.uexcel.airlinebookingreservation.repository.BookingTrackerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingServiceImp implements BookingService {
    private final BookingTrackerRepository bookingTrackerRepository;

    public BookingServiceImp(BookingTrackerRepository bookingTrackerRepository) {
        this.bookingTrackerRepository = bookingTrackerRepository;
    }

    @Override
    public ResponseEntity<List<BookingTrackerDto>> saveBookingTracker(BookingTrackerConverterDto bookingTrackerConverterDto) {

        BookingTracker bookingTracker1 = getBookingTrackerDto1(bookingTrackerConverterDto);

        if(bookingTrackerConverterDto.getAircraftNumber2() != null && bookingTrackerConverterDto.getDate2() != null){

            validateBooking1(bookingTracker1,bookingTrackerConverterDto, bookingTrackerRepository);

            BookingTracker bookingTracker2 = getBookingTrackerDto2(bookingTrackerConverterDto);

            validateBooking2(bookingTracker2,bookingTrackerConverterDto, bookingTrackerRepository);

            return ResponseEntity.ok().body(List.of(convertToDto(bookingTracker1 , bookingTrackerRepository),
                   convertToDto(bookingTracker2, bookingTrackerRepository)));
        }


        validateBooking1(bookingTracker1,bookingTrackerConverterDto, bookingTrackerRepository);

        return ResponseEntity.ok().body(List.of(convertToDto(bookingTracker1, bookingTrackerRepository)));
    }


    private static BookingTracker getBookingTrackerDto1(BookingTrackerConverterDto bookingTrackerConverterDto) {

        int year1 = bookingTrackerConverterDto.getDate1().getYear();
        int dayOfYear1 = bookingTrackerConverterDto.getDate1().getDayOfYear();

        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber1());
        bookingTracker.setDepartureTime(bookingTrackerConverterDto.getDepartureTime1());
        bookingTracker.setDayOfYear(dayOfYear1);
        bookingTracker.setSeatNumber(bookingTrackerConverterDto.getSeatNumber1());
        bookingTracker.setYear(year1);

        return bookingTracker;
    }

    private static BookingTracker getBookingTrackerDto2(BookingTrackerConverterDto bookingTrackerConverterDto) {

        int year2 = bookingTrackerConverterDto.getDate2().getYear();
        int dayOfYear2 = bookingTrackerConverterDto.getDate2().getDayOfYear();
        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber2());
        bookingTracker.setDepartureTime(bookingTrackerConverterDto.getDepartureTime2());
        bookingTracker.setDayOfYear(dayOfYear2);
        bookingTracker.setSeatNumber(bookingTrackerConverterDto.getSeatNumber2());
        bookingTracker.setYear(year2);

        return bookingTracker;

    }


    public static  BookingTrackerDto convertToDto(BookingTracker bookingTracker,
                                                  BookingTrackerRepository bookingTrackerRepository){
        BookingTrackerDto bookingTrackerDto = new BookingTrackerDto();
        bookingTrackerDto.setAircraftNumber(bookingTracker.getAircraftNumber());
        bookingTrackerDto.setSeatNumber(bookingTracker.getSeatNumber());
        bookingTrackerDto.setYear(bookingTracker.getYear());
        bookingTrackerDto.setDayOfYear(bookingTracker.getDayOfYear());
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
package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingTrackerConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingTrackerDto;
import com.uexcel.airlinebookingreservation.dto.BookingUpdateDto;
import com.uexcel.airlinebookingreservation.entity.Booking;
import com.uexcel.airlinebookingreservation.entity.BookingTracker;
import com.uexcel.airlinebookingreservation.repository.BookingRepository;
import com.uexcel.airlinebookingreservation.repository.BookingTrackerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;



@Service
public class BookingServiceImp implements BookingService {
    private final BookingTrackerRepository bookingTrackerRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImp(BookingTrackerRepository bookingTrackerRepository, BookingRepository bookingRepository) {
        this.bookingTrackerRepository = bookingTrackerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public ResponseEntity<List<BookingTrackerDto>> saveBookingTracker(BookingTrackerConverterDto bookingTrackerConverterDto) {

        Booking booking1 = convertToBooking1(bookingTrackerConverterDto);

        BookingTracker bookingTracker1 = convertToBookingTracker1(booking1);

        List<BookingTracker> bookingTrackerList =
                bookingTrackerRepository.findByAircraftNumber(bookingTrackerConverterDto.getAircraftNumber1());

        deleteOldBookings(bookingTrackerList, bookingTrackerRepository);

        if (bookingTrackerConverterDto.getAircraftNumber2() != null && bookingTrackerConverterDto.getDate2() != null) {

            List<BookingTracker> bookingTrackerList2 =
                    bookingTrackerRepository.findByAircraftNumber(bookingTrackerConverterDto.getAircraftNumber2());

            deleteOldBookings(bookingTrackerList2, bookingTrackerRepository);


            validateBooking1(bookingTracker1, booking1, bookingTrackerRepository);


            Booking booking2 = convertToBooking2(bookingTrackerConverterDto);

            BookingTracker bookingTracker2 = convertToBookingTracker1(booking2);

            validateBooking2(bookingTracker2, bookingTrackerConverterDto, bookingTrackerRepository);

            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingTrackerRepository.save(bookingTracker1);
            bookingTrackerRepository.save(bookingTracker2);

            return ResponseEntity.ok().body(List.of(convertToDto(booking1),
                    convertToDto(booking2)));
        }


        validateBooking1(bookingTracker1, booking1, bookingTrackerRepository);
         bookingRepository.save(booking1);
         bookingTrackerRepository.save(bookingTracker1);
        return ResponseEntity.ok().body(List.of(convertToDto(booking1)));
    }

    @Override
    public BookingTrackerDto updateBookingStatus(String id) {

        Booking booking =
                bookingRepository.findByBookingId(id);
        if (booking != null && booking.getStatus().equals("unused")) {
            booking.setStatus("used");

            BookingTracker bookingTracker = bookingTrackerRepository.findByBookingId(id);
            if(bookingTracker != null && bookingTracker.getStatus().equals("unused")){
                bookingTracker.setStatus("used");
                bookingTrackerRepository.save(bookingTracker);
                bookingRepository.save(booking);
                return convertToDto(booking);
            }

            BookingTracker bookingTracker1 = convertToBookingTracker1(booking);
            bookingTrackerRepository.save(bookingTracker1);
            bookingRepository.save(booking);
            return convertToDto(booking);

        }

        throw new RuntimeException("Booking not found");
    }

    @Override
    public BookingTrackerDto updateBooking(BookingUpdateDto bookingUpdateDto) {

        Booking booking =
                bookingRepository.findByBookingId(bookingUpdateDto.getBookingId());
        BookingTracker bookingTracker =
                bookingTrackerRepository.findByBookingId(bookingUpdateDto.getBookingId());

        if (booking != null && bookingTracker != null) {

            if(bookingUpdateDto.getFirstName() != null){
                booking.setFirstName(bookingUpdateDto.getFirstName());
            }

            if(bookingUpdateDto.getLastName() != null){
                booking.setLastName(booking.getLastName());
            }

            if(bookingUpdateDto.getNextOfKingNumber() != null){
                booking.setNextOfKingNumber(bookingUpdateDto.getNextOfKingNumber());
            }

            if(bookingUpdateDto.getAddress() != null){
                booking.setAddress(bookingUpdateDto.getAddress());
            }

            if (bookingUpdateDto.getStatus() != null) {
                booking.setStatus(bookingUpdateDto.getStatus());
                bookingTracker.setStatus(bookingUpdateDto.getStatus());
            }

            if(bookingUpdateDto.getSeatNumber() > 0){
                booking.setSeatNumber(bookingUpdateDto.getSeatNumber());
                bookingTracker.setSeatNumber(bookingUpdateDto.getSeatNumber());
            }

            if (bookingUpdateDto.getAircraftNumber() != null) {
                booking.setAircraftNumber(bookingUpdateDto.getAircraftNumber());
                bookingTracker.setAircraftNumber(bookingUpdateDto.getAircraftNumber());
            }

            if(bookingUpdateDto.getDepartureTime() != null){
                booking.setDepartureTime(bookingUpdateDto.getDepartureTime());
                bookingTracker.setDepartureTime(bookingUpdateDto.getDepartureTime());
            }

            if(bookingUpdateDto.getOrigin()!= null){
                booking.setOrigin(bookingUpdateDto.getOrigin());
            }

            if(bookingUpdateDto.getDate()!= null){
                booking.setDate(bookingUpdateDto.getDate());
                bookingTracker.setYear(bookingUpdateDto.getDate().getYear());
                bookingTracker.setDayOfYear(bookingUpdateDto.getDate().getDayOfYear());
            }

            if(bookingUpdateDto.getArrivalTime()!= null){
                booking.setArrivalTime(bookingUpdateDto.getArrivalTime());
            }

            if(bookingUpdateDto.getDestination() != null){
                booking.setDestination(bookingUpdateDto.getDestination());
            }

            bookingRepository.save(booking);
            bookingTrackerRepository.save(bookingTracker);
            return convertToDto(booking);

        } else  if(booking != null){

            if(bookingUpdateDto.getFirstName() != null){

                booking.setFirstName(bookingUpdateDto.getFirstName());
            }

            if(bookingUpdateDto.getLastName() != null){
                booking.setLastName(booking.getLastName());
            }

            if(bookingUpdateDto.getNextOfKingNumber() != null){
                booking.setNextOfKingNumber(bookingUpdateDto.getNextOfKingNumber());
            }

            if(bookingUpdateDto.getAddress() != null){
                booking.setAddress(bookingUpdateDto.getAddress());
            }

            if (bookingUpdateDto.getStatus() != null) {
                booking.setStatus(bookingUpdateDto.getStatus());
            }

            if(bookingUpdateDto.getSeatNumber() > 0){
                booking.setSeatNumber(bookingUpdateDto.getSeatNumber());
            }

            if (bookingUpdateDto.getAircraftNumber() != null) {
                booking.setAircraftNumber(bookingUpdateDto.getAircraftNumber());
            }

            if(bookingUpdateDto.getDepartureTime() != null){
                booking.setDepartureTime(bookingUpdateDto.getDepartureTime());
            }

            if(bookingUpdateDto.getOrigin()!= null){
                booking.setOrigin(bookingUpdateDto.getOrigin());
            }

            if(bookingUpdateDto.getDate()!= null){
                booking.setDate(bookingUpdateDto.getDate());
            }

            if(bookingUpdateDto.getArrivalTime()!= null){
                booking.setArrivalTime(bookingUpdateDto.getArrivalTime());
            }

            if(bookingUpdateDto.getDestination() != null){
                booking.setDestination(bookingUpdateDto.getDestination());
            }

            BookingTracker bookingTracker1 = convertToBookingTracker1(booking);
            validateBooking1(bookingTracker1, booking, bookingTrackerRepository);
            bookingRepository.save(booking);
            bookingTrackerRepository.save(bookingTracker1);
            return convertToDto(booking);

        }

        throw new RuntimeException("Booking not found");
    }



    private static BookingTracker convertToBookingTracker1(Booking booking) {

        int year1 = booking.getDate().getYear();
        int dayOfYear1 = booking.getDate().getDayOfYear();

        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setBookingId(booking.getBookingId());
        bookingTracker.setAircraftNumber(booking.getAircraftNumber());
        bookingTracker.setDepartureTime(booking.getDepartureTime());
        bookingTracker.setDayOfYear(dayOfYear1);
        bookingTracker.setSeatNumber(booking.getSeatNumber());
        bookingTracker.setYear(year1);
        bookingTracker.setStatus(booking.getStatus());

        return bookingTracker;
    }

    private static BookingTracker convertToBookingTracker2(
            BookingTrackerConverterDto bookingTrackerConverterDto, String bookingId) {

        int year2 = bookingTrackerConverterDto.getDate2().getYear();
        int dayOfYear2 = bookingTrackerConverterDto.getDate2().getDayOfYear();
        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setBookingId(bookingId);
        bookingTracker.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber2());
        bookingTracker.setDepartureTime(bookingTrackerConverterDto.getDepartureTime2());
        bookingTracker.setDayOfYear(dayOfYear2);
        bookingTracker.setSeatNumber(bookingTrackerConverterDto.getSeatNumber2());
        bookingTracker.setYear(year2);
        bookingTracker.setStatus("unused");

        return bookingTracker;

    }


    public static  BookingTrackerDto convertToDto(Booking booking){

        BookingTrackerDto bookingTrackerDto = new BookingTrackerDto();
        bookingTrackerDto.setFirstName(booking.getFirstName());
        bookingTrackerDto.setLastName(booking.getLastName());
        bookingTrackerDto.setNextOfKingNumber(booking.getNextOfKingNumber());
        bookingTrackerDto.setAddress(booking.getAddress());
        bookingTrackerDto.setBookingId(booking.getBookingId());
        bookingTrackerDto.setAircraftNumber(booking.getAircraftNumber());
        bookingTrackerDto.setSeatNumber(booking.getSeatNumber());
        bookingTrackerDto.setDate(booking.getDate());
        bookingTrackerDto.setDepartureTime(booking.getDepartureTime());
        bookingTrackerDto.setOrigin(booking.getOrigin());
        bookingTrackerDto.setDestination(booking.getDestination());
        bookingTrackerDto.setArrivalTime(booking.getArrivalTime());
        bookingTrackerDto.setStatus(booking.getStatus());
        bookingTrackerDto.setFirstName(booking.getFirstName());


        return  bookingTrackerDto;
    }

    public  static  void validateBooking1(
            BookingTracker bookingTracker, Booking booking,
    BookingTrackerRepository bookingTrackerRepository) {
            BookingTracker newBooking = bookingTrackerRepository.findByGo(
                    bookingTracker.getAircraftNumber(), bookingTracker.getSeatNumber(),
                    bookingTracker.getYear(), bookingTracker.getDayOfYear(),
                    bookingTracker.getDepartureTime());

            if (newBooking != null) {
                throw new RuntimeException("The seat on " + booking.getAircraftNumber()
                        + " has been booked on this date " + booking.getDate());
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

    public static void deleteOldBookings(
            List<BookingTracker> bookingTrackers, BookingTrackerRepository bookingTrackerRepository){
        for(BookingTracker n: bookingTrackers){
            if(n.getDayOfYear() < LocalDate.now().getDayOfYear() && n.getYear() == LocalDate.now().getYear()){
                bookingTrackerRepository.delete(n);
            }
            if(n.getYear() < LocalDate.now().getYear()){
                bookingTrackerRepository.delete(n);
            }
        }
    }

    public static Booking convertToBooking1(
            BookingTrackerConverterDto bookingTrackerConverterDto){

        Booking booking = new Booking();
        booking.setFirstName(bookingTrackerConverterDto.getFirstName());
        booking.setLastName(bookingTrackerConverterDto.getLastName());
        booking.setNextOfKingNumber(bookingTrackerConverterDto.getNextOfKingNumber());
        booking.setAddress(bookingTrackerConverterDto.getAddress());
        booking.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber1());
        booking.setSeatNumber(bookingTrackerConverterDto.getSeatNumber1());
        booking.setDepartureTime(bookingTrackerConverterDto.getDepartureTime1());
        booking.setDate(bookingTrackerConverterDto.getDate1());
        booking.setOrigin(bookingTrackerConverterDto.getFrom1());
        booking.setDestination(bookingTrackerConverterDto.getDestination1());
        booking.setArrivalTime(bookingTrackerConverterDto.getArrivalTime1());
        booking.setStatus("unused");
        return booking;
    }

    public static Booking convertToBooking2(
            BookingTrackerConverterDto bookingTrackerConverterDto){

        Booking booking = new Booking();
        booking.setFirstName(bookingTrackerConverterDto.getFirstName());
        booking.setLastName(bookingTrackerConverterDto.getLastName());
        booking.setAddress(bookingTrackerConverterDto.getAddress());
        booking.setAircraftNumber(bookingTrackerConverterDto.getAircraftNumber2());
        booking.setSeatNumber(bookingTrackerConverterDto.getSeatNumber2());
        booking.setDepartureTime(bookingTrackerConverterDto.getDepartureTime2());
        booking.setDate(bookingTrackerConverterDto.getDate2());
        booking.setOrigin(bookingTrackerConverterDto.getFrom2());
        booking.setDestination(bookingTrackerConverterDto.getDestination2());
        booking.setArrivalTime(bookingTrackerConverterDto.getArrivalTime2());
        booking.setStatus("unused");
        return booking;
    }
}
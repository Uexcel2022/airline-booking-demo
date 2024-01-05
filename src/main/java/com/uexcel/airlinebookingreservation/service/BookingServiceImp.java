package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingDto;
import com.uexcel.airlinebookingreservation.entity.Booking;
import com.uexcel.airlinebookingreservation.entity.BookingTracker;
import com.uexcel.airlinebookingreservation.repository.BookingRepository;
import com.uexcel.airlinebookingreservation.repository.BookingTrackerRepository;
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
    public List<BookingDto> saveBooking(BookingConverterDto bookingConverterDto) {

        dateValidation(bookingConverterDto.getDate1().getYear(), bookingConverterDto.getDate1().getDayOfYear());

        if(bookingConverterDto.getAircraftNumber2() != null) {
            dateValidation(bookingConverterDto.getDate2().getYear(), bookingConverterDto.getDate2().getDayOfYear());
        }

        Booking booking1 = convertToBooking1(bookingConverterDto);

        BookingTracker bookingTracker1 = convertToBookingTracker1(booking1);

        List<BookingTracker> bookingTrackerList =
                bookingTrackerRepository.findByAircraftNumber(bookingConverterDto.getAircraftNumber1());

        deleteOldBookings(bookingTrackerList, bookingTrackerRepository);

        if (bookingConverterDto.getAircraftNumber2() != null && bookingConverterDto.getDate2() != null) {

            List<BookingTracker> bookingTrackerList2 =
                    bookingTrackerRepository.findByAircraftNumber(bookingConverterDto.getAircraftNumber2());

            deleteOldBookings(bookingTrackerList2, bookingTrackerRepository);

            validateBooking1(bookingTracker1, booking1, bookingTrackerRepository);

            Booking booking2 = convertToBooking2(bookingConverterDto);

            BookingTracker bookingTracker2 = convertToBookingTracker1(booking2);

            validateBooking1(bookingTracker2, booking2, bookingTrackerRepository);

            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingTrackerRepository.save(bookingTracker1);
            bookingTrackerRepository.save(bookingTracker2);

            return List.of(convertToDto(booking1),
                    convertToDto(booking2));
        }

        validateBooking1(bookingTracker1, booking1, bookingTrackerRepository);
         bookingRepository.save(booking1);
         bookingTrackerRepository.save(bookingTracker1);
        return List.of(convertToDto(booking1));

    }

    @Override
    public BookingDto updateBookingStatus(String id) {

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
    public BookingDto updateBooking(BookingDto bookingDto) {

        Booking booking =
                bookingRepository.findByBookingId(bookingDto.getBookingId());
        BookingTracker bookingTracker =
                bookingTrackerRepository.findByBookingId(bookingDto.getBookingId());

        if (booking != null && bookingTracker != null) {

            if(bookingDto.getFirstName() != null ){
                booking.setFirstName(bookingDto.getFirstName());
            }

            if(bookingDto.getLastName() != null){
                booking.setLastName(bookingDto.getLastName());
            }

            if(bookingDto.getNextOfKingNumber() != null){
                booking.setNextOfKingNumber(bookingDto.getNextOfKingNumber());
            }

            if(bookingDto.getAddress() != null){
                booking.setAddress(bookingDto.getAddress());
            }

            if (bookingDto.getStatus() != null) {
                booking.setStatus(bookingDto.getStatus());
                bookingTracker.setStatus(bookingDto.getStatus());
            }

            if(bookingDto.getSeatNumber() > 0){
                booking.setSeatNumber(bookingDto.getSeatNumber());
                bookingTracker.setSeatNumber(bookingDto.getSeatNumber());
            }

            if (bookingDto.getAircraftNumber() != null) {
                booking.setAircraftNumber(bookingDto.getAircraftNumber());
                bookingTracker.setAircraftNumber(bookingDto.getAircraftNumber());
            }

            if(bookingDto.getDepartureTime() != null){
                booking.setDepartureTime(bookingDto.getDepartureTime());
                bookingTracker.setDepartureTime(bookingDto.getDepartureTime());
            }

            if(bookingDto.getOrigin()!= null){
                booking.setOrigin(bookingDto.getOrigin());
            }

            if(bookingDto.getDate()!= null){
                booking.setDate(bookingDto.getDate());
                bookingTracker.setYear(bookingDto.getDate().getYear());
                bookingTracker.setDayOfYear(bookingDto.getDate().getDayOfYear());
            }

            if(bookingDto.getArrivalTime()!= null){
                booking.setArrivalTime(bookingDto.getArrivalTime());
            }

            if(bookingDto.getDestination() != null){
                booking.setDestination(bookingDto.getDestination());
            }

            if(bookingDto.getEmail() != null){
                booking.setEmail(bookingDto.getEmail());
            }

            bookingRepository.save(booking);
            bookingTrackerRepository.save(bookingTracker);
            return convertToDto(booking);

        } else  if(booking != null){

            if(bookingDto.getFirstName() != null){

                booking.setFirstName(bookingDto.getFirstName());
            }

            if(bookingDto.getLastName() != null){
                booking.setLastName(booking.getLastName());
            }

            if(bookingDto.getNextOfKingNumber() != null){
                booking.setNextOfKingNumber(bookingDto.getNextOfKingNumber());
            }

            if(bookingDto.getAddress() != null){
                booking.setAddress(bookingDto.getAddress());
            }

            if (bookingDto.getStatus() != null) {
                booking.setStatus(bookingDto.getStatus());
            }

            if(bookingDto.getSeatNumber() > 0){
                booking.setSeatNumber(bookingDto.getSeatNumber());
            }

            if (bookingDto.getAircraftNumber() != null) {
                booking.setAircraftNumber(bookingDto.getAircraftNumber());
            }

            if(bookingDto.getDepartureTime() != null){
                booking.setDepartureTime(bookingDto.getDepartureTime());
            }

            if(bookingDto.getOrigin()!= null){
                booking.setOrigin(bookingDto.getOrigin());
            }

            if(bookingDto.getDate()!= null){
                booking.setDate(bookingDto.getDate());
            }

            if(bookingDto.getArrivalTime()!= null){
                booking.setArrivalTime(bookingDto.getArrivalTime());
            }

            if(bookingDto.getDestination() != null){
                booking.setDestination(bookingDto.getDestination());
            }

            if(bookingDto.getEmail() != null){
                booking.setEmail(bookingDto.getEmail());
            }

            BookingTracker bookingTracker1 = convertToBookingTracker1(booking);
            validateBooking1(bookingTracker1, booking, bookingTrackerRepository);
            bookingRepository.save(booking);
            bookingTrackerRepository.save(bookingTracker1);
            return convertToDto(booking);

        }

        throw new RuntimeException("Booking not found");
    }

    @Override
    public BookingDto checkBooking(String id) {
        return convertToDto(bookingRepository.findByBookingId(id));
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
            BookingConverterDto bookingConverterDto, String bookingId) {

        int year2 = bookingConverterDto.getDate2().getYear();
        int dayOfYear2 = bookingConverterDto.getDate2().getDayOfYear();
        BookingTracker bookingTracker = new BookingTracker();
        bookingTracker.setBookingId(bookingId);
        bookingTracker.setAircraftNumber(bookingConverterDto.getAircraftNumber2());
        bookingTracker.setDepartureTime(bookingConverterDto.getDepartureTime2());
        bookingTracker.setDayOfYear(dayOfYear2);
        bookingTracker.setSeatNumber(bookingConverterDto.getSeatNumber2());
        bookingTracker.setYear(year2);
        bookingTracker.setStatus("unused");

        return bookingTracker;

    }


    public static BookingDto convertToDto(Booking booking){

        BookingDto bookingDto = new BookingDto();
        bookingDto.setFirstName(booking.getFirstName());
        bookingDto.setLastName(booking.getLastName());
        bookingDto.setNextOfKingNumber(booking.getNextOfKingNumber());
        bookingDto.setAddress(booking.getAddress());
        bookingDto.setBookingId(booking.getBookingId());
        bookingDto.setAircraftNumber(booking.getAircraftNumber());
        bookingDto.setSeatNumber(booking.getSeatNumber());
        bookingDto.setDate(booking.getDate());
        bookingDto.setDepartureTime(booking.getDepartureTime());
        bookingDto.setOrigin(booking.getOrigin());
        bookingDto.setDestination(booking.getDestination());
        bookingDto.setArrivalTime(booking.getArrivalTime());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setPrice(booking.getPrice());
        bookingDto.setEmail(booking.getEmail());

        return bookingDto;
    }

    public  static  void validateBooking1(
            BookingTracker bookingTracker, Booking booking,
    BookingTrackerRepository bookingTrackerRepository) {
            BookingTracker newBooking = bookingTrackerRepository.findByGo(
                    bookingTracker.getAircraftNumber(), bookingTracker.getSeatNumber(),
                    bookingTracker.getYear(), bookingTracker.getDayOfYear(),
                    bookingTracker.getDepartureTime());
            if (newBooking != null) {
                throw new RuntimeException("The seat number " +booking.getSeatNumber()+ " on aircraft number " +
                        booking.getAircraftNumber() + " has been booked on this date " + booking.getDate());
            }

    }

    public  static  void validateBooking2(
            BookingTracker bookingTracker, BookingConverterDto bookingConverterDto,
            BookingTrackerRepository bookingTrackerRepository) {
        BookingTracker booking = bookingTrackerRepository.findByGo(
                bookingTracker.getAircraftNumber(), bookingTracker.getSeatNumber(),
                bookingTracker.getYear(), bookingTracker.getDayOfYear(),
                bookingTracker.getDepartureTime());

        if (booking != null) {
            throw new RuntimeException("The seat on " + bookingConverterDto.getAircraftNumber2()
                    + " has been booked on this date " + bookingConverterDto.getDate2());
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
            BookingConverterDto bookingConverterDto){

        Booking booking = new Booking();
        booking.setFirstName(bookingConverterDto.getFirstName());
        booking.setLastName(bookingConverterDto.getLastName());
        booking.setNextOfKingNumber(bookingConverterDto.getNextOfKingNumber());
        booking.setAddress(bookingConverterDto.getAddress());
        booking.setAircraftNumber(bookingConverterDto.getAircraftNumber1());
        booking.setSeatNumber(bookingConverterDto.getSeatNumber1());
        booking.setDepartureTime(bookingConverterDto.getDepartureTime1());
        booking.setDate(bookingConverterDto.getDate1());
        booking.setOrigin(bookingConverterDto.getFrom1());
        booking.setDestination(bookingConverterDto.getDestination1());
        booking.setArrivalTime(bookingConverterDto.getArrivalTime1());
        booking.setPrice(bookingConverterDto.getPrice());
        booking.setEmail(bookingConverterDto.getEmail());
        booking.setStatus("unused");
        return booking;
    }

    public static Booking convertToBooking2(
            BookingConverterDto bookingConverterDto){

        Booking booking = new Booking();
        booking.setFirstName(bookingConverterDto.getFirstName());
        booking.setLastName(bookingConverterDto.getLastName());
        booking.setAddress(bookingConverterDto.getAddress());
        booking.setAircraftNumber(bookingConverterDto.getAircraftNumber2());
        booking.setSeatNumber(bookingConverterDto.getSeatNumber2());
        booking.setDepartureTime(bookingConverterDto.getDepartureTime2());
        booking.setDate(bookingConverterDto.getDate2());
        booking.setOrigin(bookingConverterDto.getFrom2());
        booking.setDestination(bookingConverterDto.getDestination2());
        booking.setArrivalTime(bookingConverterDto.getArrivalTime2());
        booking.setStatus("unused");
        return booking;
    }

    private  void dateValidation(int year,  int dayOfYear){

        if(year < LocalDate.now().getYear()){
            throw new RuntimeException("Invalid booking date");
        }

        if(year == LocalDate.now().getYear() && dayOfYear < LocalDate.now().getDayOfYear()){
            throw new RuntimeException("Invalid booking date");
        }

    }
}
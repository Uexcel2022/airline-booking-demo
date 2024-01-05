package com.uexcel.airlinebookingreservation.service;


import com.uexcel.airlinebookingreservation.dto.BookingConverterDto;
import com.uexcel.airlinebookingreservation.dto.BookingDto;

import java.util.List;


public interface BookingService {
    List<BookingDto> saveBooking(BookingConverterDto bookingConverterDto);

    BookingDto updateBookingStatus(String id);

    BookingDto updateBooking(BookingDto bookingUpdateDto);

    BookingDto checkBooking(String id);
}

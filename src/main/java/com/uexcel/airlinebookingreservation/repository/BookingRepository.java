package com.uexcel.airlinebookingreservation.repository;

import com.uexcel.airlinebookingreservation.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
    Booking findByBookingId(String id);
}

package com.uexcel.airlinebookingreservation.repository;


import com.uexcel.airlinebookingreservation.entity.BookingTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookingTrackerRepository extends JpaRepository<BookingTracker, Long> {

    BookingTracker findByBookingId(String id);
    BookingTracker deleteByBookingId(String id);
    @Query(
            nativeQuery = true,
           value = "SELECT * From booking_tracker WHERE aircraft_number=:aircraftNumber AND seat_number =:seatNumber " +
                    "AND year=:year AND day_of_year=:dayOfYear AND departure_time=:departureTime"
    )
    BookingTracker findByGo(
           @Param("aircraftNumber") String aircraftNumber,
           @Param("seatNumber") int seatNumber,
           @Param("year") int year,
           @Param("dayOfYear") int dayOfYear,
           @Param("departureTime") String departureTime
    );


    @Query(
            "SELECT p.seatNumber From BookingTracker p WHERE p.aircraftNumber =:aircraft AND" +
                    " p.year  =:yr AND p.dayOfYear =:doy AND p.departureTime =:departure"
    )
    List<Integer> booking(
            @Param("aircraft") String aircraft,
            @Param("yr") int yr,
            @Param("doy") int doy,
            @Param("departure") String departure
    );

    List<BookingTracker> findByAircraftNumber(String aircraftNumber1);
}

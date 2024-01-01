package com.uexcel.airlinebookingreservation.repository;


import com.uexcel.airlinebookingreservation.entity.BookingTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookingTrackerRepository extends JpaRepository<BookingTracker, String> {

    List<BookingTracker> findBySeatNumber(int no);

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

//    @Query(
//            "SELECT * From booking_tracker WHERE aircraft_number=:aircraftNumber AND seat_number =:seatNumber " +
//                    "AND to_year=:toYear AND to_day_of_year=:toDayOfYear AND fro_year=:froYear " +
//                    "AND fro_day_of_year=:froDayOfYear AND departure_time=:departureTime"
//    )
//    Mono<BookingTracker> findByReturn(
//            @Param("aircraftNumber") String aircraftNumber,
//            @Param("seatNumber") int seatNumber,
//            @Param("toYear") int toYear,
//            @Param("toDayOfYear") int toDayOfYear,
//            @Param("froYear") int froYear,
//            @Param("froDayOfYear") int froDayOfYear,
//            @Param("departureTime") String departureTime

//    );
}

package com.uexcel.airlinebookingreservation.repository;

import com.uexcel.airlinebookingreservation.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    @Query( nativeQuery = true, value = "SELECT AD473N6 FROM seat")
    List <Integer> AD473N6();
}

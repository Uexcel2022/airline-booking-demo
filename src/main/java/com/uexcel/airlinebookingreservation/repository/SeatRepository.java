package com.uexcel.airlinebookingreservation.repository;

import com.uexcel.airlinebookingreservation.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    @Query( "SELECT p.AD473N6 FROM Seat p")
    List <Integer> AD473N6();


    @Query("SELECT p.DC407N1 FROM Seat p")
    List <Integer> DC407N1();

}

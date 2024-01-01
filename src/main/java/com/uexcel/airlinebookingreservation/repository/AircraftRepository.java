package com.uexcel.airlinebookingreservation.repository;

import com.uexcel.airlinebookingreservation.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft,Long> {
   List<Aircraft> findByAircraftNumber(String seatNumber);
}

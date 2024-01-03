package com.uexcel.airlinebookingreservation.repository;

import com.uexcel.airlinebookingreservation.entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule,Long> {
    List<FlightSchedule> findByWeekDay(String dayOfWeek);
}

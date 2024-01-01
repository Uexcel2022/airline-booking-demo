package com.uexcel.airlinebookingreservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aircraftNumber;
    private int s1;
    private int s2;
    private int s3;
    private int s4;
    private int s5;
    private int s6;
    private int s7;
    private int s8;
    private int s9;
    private int s10;
    private int s11;
    private int s12;
    private int s13;
    private int s14;
    private int s15;
    private int s16;
    private int s17;
    private int s18;
    private int s19;
    private int s20;
}
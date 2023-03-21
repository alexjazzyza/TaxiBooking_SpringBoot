package com.example.taxibooking.payloads;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationConfirmation {

    private Long customerId;

    private Long driverId;

    private String departureAddress;

    private String destinationAddress;

    private String departureDateTime;

    private String arrivalDateTime;

    private Integer estimatedTime;

    private Float Price;
}

package com.example.taxibooking.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequest {

    private Long customerId;

    private String departureAddress;

    private String destinationAddress;

    private Date departureDateTime;

    private String arrivalDateTime;

    private Integer estimatedTime;

    private Float Price;
}

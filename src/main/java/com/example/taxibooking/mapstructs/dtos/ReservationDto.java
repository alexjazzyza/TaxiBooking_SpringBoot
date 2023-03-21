package com.example.taxibooking.mapstructs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservationDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer")
    private CustomerGetDto customer;

    @JsonProperty("driver")
    private DriverGetDto driver;

    @JsonProperty("departureAddress")
    private String departureAddress;

    @JsonProperty("destinationAddress")
    private String destinationAddress;

    @JsonProperty("departureDateTime")
    private Date departureDateTime;

    @JsonProperty("estimatedTime")
    private Integer estimatedTime;

    @JsonProperty("estimatedPrice")
    private Float estimatedPrice;
}

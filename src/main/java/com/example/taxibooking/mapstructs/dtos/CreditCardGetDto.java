package com.example.taxibooking.mapstructs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("customerId")
    private CustomerGetDto customer;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cardNumber")
    private String cardNumber;

    @JsonProperty("expirationDate")
    private String expirationDate;
}

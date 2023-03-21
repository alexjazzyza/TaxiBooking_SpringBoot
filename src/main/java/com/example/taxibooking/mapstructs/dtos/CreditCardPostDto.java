package com.example.taxibooking.mapstructs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardPostDto {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("customerId")
    private CustomerGetDto customer;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("cardNumber")
    private String cardNumber;

    @NotNull
    @JsonProperty("expirationDate")
    private String expirationDate;

    @NotNull
    @JsonProperty("cvc")
    private String cvc;
}

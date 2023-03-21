package com.example.taxibooking.payloads;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 2, max = 40)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank
    @Size(min = 10, max = 10)
    private String phoneNumber;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    private String password;

    public SignUpRequest(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SignUpRequest signUpRequest = objectMapper.readValue(json, SignUpRequest.class);
            this.email = signUpRequest.email;
            this.firstName = signUpRequest.firstName;
            this.lastName = signUpRequest.lastName;
            this.password = signUpRequest.password;
            this.phoneNumber = signUpRequest.phoneNumber;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

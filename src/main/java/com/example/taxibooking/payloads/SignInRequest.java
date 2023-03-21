package com.example.taxibooking.payloads;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private Boolean containsPassword;
    private String usernameOrEmail;
    private String password;
    private String refreshToken;

    public SignInRequest(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SignInRequest signInRequest = objectMapper.readValue(json, SignInRequest.class);
            this.containsPassword = signInRequest.containsPassword;
            this.usernameOrEmail = signInRequest.usernameOrEmail;
            this.password = signInRequest.password;
            this.refreshToken = signInRequest.refreshToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

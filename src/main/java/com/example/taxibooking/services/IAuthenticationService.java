package com.example.taxibooking.services;

import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.payloads.JwtAuthenticationResponse;
import com.example.taxibooking.payloads.SignInRequest;
import com.example.taxibooking.payloads.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {

    ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest signInRequest);
    ResponseEntity<ApiResponse> signUp(SignUpRequest signUpRequest);
    ApiResponse logOut(HttpServletRequest request, HttpServletResponse response);
}

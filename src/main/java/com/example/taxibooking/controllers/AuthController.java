package com.example.taxibooking.controllers;

import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.payloads.JwtAuthenticationResponse;
import com.example.taxibooking.payloads.SignInRequest;
import com.example.taxibooking.payloads.SignUpRequest;
import com.example.taxibooking.services.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final IAuthenticationService authenticationService;

    public AuthController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("sign-in/")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@Valid @RequestBody SignInRequest signUpRequest) {
        return authenticationService.signIn(signUpRequest);
    }

    @PostMapping("sign-up/")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @GetMapping(value="logout/")
    public ResponseEntity<ApiResponse> logOut(HttpServletRequest request, HttpServletResponse response) {
            return ResponseEntity.ok(authenticationService.logOut(request, response));
    }

}

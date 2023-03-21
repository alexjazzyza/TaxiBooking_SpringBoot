package com.example.taxibooking.services.implementations;

import com.example.taxibooking.exceptions.AppException;
import com.example.taxibooking.exceptions.TaxiBookingApiException;
import com.example.taxibooking.models.role.ERoleName;
import com.example.taxibooking.models.role.Authority;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.payloads.JwtAuthenticationResponse;
import com.example.taxibooking.payloads.SignInRequest;
import com.example.taxibooking.payloads.SignUpRequest;
import com.example.taxibooking.repositories.*;
import com.example.taxibooking.security.JwtTokenProvider;
import com.example.taxibooking.services.IAuthenticationService;
import com.example.taxibooking.services.ICustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationService implements IAuthenticationService {

    private final JwtDecoder jwtDecoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ICustomUserDetailsService customUserDetailsService;
    private final IUserRepository userRepository;
    private final ICustomerRepository customerRepository;
    private final IAuthorityRepository authorityRepository;
    private static final String USER_ROLE_NOT_SET = "User role not set";
    private static final Integer ACCESS_TOKEN_EXPIRATION_TIME_IN_MIN = 1;
    private static final Integer REFRESH_TOKEN_EXPIRATION_TIME_IN_MIN = 43200; // 30 days


    public AuthenticationService(JwtDecoder jwtDecoder, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, ICustomUserDetailsService customUserDetailsService, IUserRepository userRepository, ICustomerRepository customerRepository, IAuthorityRepository authorityRepository) {
        this.jwtDecoder = jwtDecoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.authorityRepository = authorityRepository;
    }


    @Override
    public ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest signInRequest) {

        String subject = null;
        String scope = null;

        if (Boolean.TRUE.equals(signInRequest.getContainsPassword())) {
            Authentication authentication = null;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsernameOrEmail(), signInRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException e) {
                throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
            subject = authentication.getName();
            scope = authentication.getAuthorities().
                    stream().map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(" "));

        } else if (Boolean.FALSE.equals(signInRequest.getContainsPassword())) {
            if (signInRequest.getRefreshToken() == null) {
                throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "No password nor refresh token found.");
            }

            Jwt decodedJwt = null;
            try {
                decodedJwt = jwtDecoder.decode(signInRequest.getRefreshToken());
            } catch (JwtException e) {
                throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "Invalid refresh token.");
            }

            try {
                subject = decodedJwt.getSubject();
            } catch (Exception e) {
                throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "No subject found from refresh token.");
            }

            UserDetails userDetails = null;
            try {
                userDetails = customUserDetailsService.loadUserByUsername(subject);
            } catch (UsernameNotFoundException e) {
                throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "No user found from refresh token.");
            }

            Collection<? extends GrantedAuthority> authorities = null;
            try {
                authorities = userDetails.getAuthorities();
            } catch (Exception e) {
                throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "No authorities found from refresh token.");
            }
            System.out.println("refresh token received");
            scope = authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        } else {
            throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "Incomplete JSON request.");
        }

        String jwtAccessToken = jwtTokenProvider.generateToken(subject, scope, ACCESS_TOKEN_EXPIRATION_TIME_IN_MIN);
        String jwtRefreshToken = jwtTokenProvider.generateToken(subject, scope, REFRESH_TOKEN_EXPIRATION_TIME_IN_MIN);
        System.out.println(jwtAccessToken);
        System.out.println(jwtRefreshToken);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtAccessToken, jwtRefreshToken));
    }

    @Override
    public ResponseEntity<ApiResponse> signUp(SignUpRequest signUpRequest) {

        if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber()))) {
            throw new TaxiBookingApiException(HttpStatus.BAD_REQUEST, "An account using this phone number already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new TaxiBookingApiException(HttpStatus.BAD_REQUEST, "An account using this email already exists");
        }

        String firstName = signUpRequest.getFirstName();
        String lastName = signUpRequest.getLastName();
        String phoneNumber = signUpRequest.getPhoneNumber();
        String email = signUpRequest.getEmail();
        String password = passwordEncoder.encode(signUpRequest.getPassword());

        Customer customer = new Customer(firstName, lastName, phoneNumber, email, password);
        List<Authority> authorities = new ArrayList<>();

        authorities.add(authorityRepository.findByName(ERoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

        customer.setAuthorities(authorities);
        Customer customerSaved = customerRepository.save(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/customers/{customerId}")
                .buildAndExpand(customerSaved.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "Customer registered successfully"));
    }

    public ApiResponse logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return new ApiResponse(true, "User is logged out.");
        }
        return new ApiResponse(false, "An error occurred during log out.");
    }
}

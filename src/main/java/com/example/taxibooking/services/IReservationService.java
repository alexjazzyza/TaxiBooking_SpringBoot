package com.example.taxibooking.services;

import com.example.taxibooking.models.Reservation;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.payloads.ReservationConfirmation;
import com.example.taxibooking.payloads.ReservationRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public interface IReservationService {

    ReservationConfirmation saveReservation(ReservationConfirmation reservationConfirmation) throws ResourceNotFoundException;
    Reservation getReservationById(Long id) throws ResourceNotFoundException, IllegalArgumentException;
    Reservation updateReservation(Long id, Reservation reservation) throws ResourceNotFoundException, IllegalArgumentException;
    ApiResponse deleteReservation(Long id) throws ResourceNotFoundException, IllegalArgumentException;
    ReservationRequest transferReservationRequest(ReservationRequest reservationRequest);
    ReservationConfirmation transferReservationConfirmation(ReservationConfirmation reservationConfirmation);

}

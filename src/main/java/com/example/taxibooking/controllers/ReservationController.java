package com.example.taxibooking.controllers;

import com.example.taxibooking.mapstructs.dtos.ReservationDto;
import com.example.taxibooking.mapstructs.mappers.IReservationMapper;
import com.example.taxibooking.models.Reservation;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.payloads.ReservationConfirmation;
import com.example.taxibooking.services.IReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations/")
public class ReservationController {

    private IReservationService reservationService;

    private IReservationMapper reservationMapper;

    public ReservationController(IReservationService reservationService, IReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping()
    public ResponseEntity<ReservationConfirmation> createReservation(@Valid @RequestBody ReservationConfirmation reservationConfirmation){
        return new ResponseEntity<>(reservationService.saveReservation(reservationConfirmation), HttpStatus.CREATED);
    }

    @GetMapping("{id}/")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(reservationMapper.modelToDto(reservationService.getReservationById(id)), HttpStatus.OK);
    }

    @PutMapping("{id}/")
    public ResponseEntity<Reservation> updateReservation(@PathVariable(name = "id") Long id, @Valid @RequestBody ReservationDto reservationDto){
        return new ResponseEntity<>(reservationService.updateReservation(id, reservationMapper.dtoToModel(reservationDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<ApiResponse> deleteReservation(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(reservationService.deleteReservation(id), HttpStatus.OK);
    }
}

package com.example.taxibooking.services.implementations;

import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.Driver;
import com.example.taxibooking.models.Reservation;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.payloads.ReservationConfirmation;
import com.example.taxibooking.payloads.ReservationRequest;
import com.example.taxibooking.repositories.ICustomerRepository;
import com.example.taxibooking.repositories.IDriverRepository;
import com.example.taxibooking.repositories.IReservationRepository;
import com.example.taxibooking.services.IReservationService;
import com.example.taxibooking.utils.DateFormatter;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ReservationService implements IReservationService {

    private IReservationRepository reservationRepository;

    private ICustomerRepository customerRepository;

    private IDriverRepository driverRepository;

    private DateFormatter dateFormatter = new DateFormatter();

    public ReservationService(IReservationRepository reservationRepository, ICustomerRepository customerRepository, IDriverRepository driverRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public ReservationConfirmation saveReservation(ReservationConfirmation reservationConfirmation) {
        Customer customer = customerRepository.findById(reservationConfirmation.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException());
        Driver driver = driverRepository.findById(reservationConfirmation.getDriverId())
                        .orElseThrow(() -> new ResourceNotFoundException());

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setDriver(driver);
        reservation.setDepartureAddress(reservationConfirmation.getDepartureAddress());
        reservation.setDestinationAddress(reservationConfirmation.getDestinationAddress());
        reservation.setDepartureDateTime(dateFormatter.stringToDateFormatter(reservationConfirmation.getDepartureDateTime()));
        reservation.setEstimatedTime(reservationConfirmation.getEstimatedTime());
        reservation.setPrice(reservationConfirmation.getPrice());
        reservationRepository.save(reservation);

        return reservationConfirmation;
    }

    @Override
    public Reservation getReservationById(Long id) throws ResourceNotFoundException {
        Reservation reservation = new Reservation();
        if (id != null) {
            Optional<Reservation> optional = reservationRepository.findById(id);
            if (optional != null && optional.isPresent()) {
                reservation = optional.get();
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return reservation;
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) throws ResourceNotFoundException {
        Reservation updateReservation = new Reservation();
        if (id != null) {
            Optional<Reservation> optional = reservationRepository.findById(id);
            if (optional != null && optional.isPresent()) {
                updateReservation = optional.get();
                updateReservation.setCustomer(reservation.getCustomer());
                updateReservation.setDriver(reservation.getDriver());
                updateReservation.setDepartureAddress(reservation.getDepartureAddress());
                updateReservation.setDestinationAddress(reservation.getDestinationAddress());
                updateReservation.setDepartureDateTime(reservation.getDepartureDateTime());
                updateReservation.setEstimatedTime(reservation.getEstimatedTime());
                updateReservation.setPrice(reservation.getPrice());
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return updateReservation;
    }

    @Override
    public ApiResponse deleteReservation(Long id) throws ResourceNotFoundException {
        if (id != null) {
            Optional<Reservation> optional = reservationRepository.findById(id);
            if (optional != null && optional.isPresent()) {
                reservationRepository.deleteById(id);
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return new ApiResponse(Boolean.TRUE, "Reservation has been successfully deleted.");
    }

    @Override
    public ReservationRequest transferReservationRequest(ReservationRequest reservationRequest) {
        return null;
    }

    @Override
    public ReservationConfirmation transferReservationConfirmation(ReservationConfirmation reservationConfirmation) {
        return null;
    }
}

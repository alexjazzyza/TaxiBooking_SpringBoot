package com.example.taxibooking.services;

import com.example.taxibooking.models.users.Driver;
import com.example.taxibooking.payloads.ApiResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public interface IDriverService {

    Driver saveDriver(Driver driver);
    Driver getDriverById(Long id) throws ResourceNotFoundException;
    Driver updateDriver(Long id, Driver driver) throws ResourceNotFoundException;
    ApiResponse deleteDriver(Long id) throws ResourceNotFoundException;
}

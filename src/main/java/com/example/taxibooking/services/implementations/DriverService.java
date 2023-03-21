package com.example.taxibooking.services.implementations;

import com.example.taxibooking.models.users.Driver;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.repositories.IDriverRepository;
import com.example.taxibooking.services.IDriverService;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DriverService implements IDriverService {

    private IDriverRepository driverRepository;

    public DriverService(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver getDriverById(Long id) throws ResourceNotFoundException {
        Driver driver = new Driver();
        if (id != null) {
            Optional<Driver> optional = driverRepository.findById(id);
            if (optional.isPresent()) {
                driver = optional.get();
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        return driver;
    }

    @Override
    public Driver updateDriver(Long id, Driver driver) throws ResourceNotFoundException {
        Driver updateDriver = new Driver();
        if (id != null) {
            Optional<Driver> optional = driverRepository.findById(id);
            if (optional.isPresent()) {
                updateDriver = optional.get();
                updateDriver.setFirstName(driver.getFirstName());
                updateDriver.setLastName(driver.getLastName());
                updateDriver.setEmail(driver.getEmail());
                updateDriver.setPhoneNumber(driver.getPhoneNumber());
                updateDriver.setPassword(driver.getPassword());
                updateDriver.setPicture(driver.getPicture());
                driverRepository.save(updateDriver);
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        return updateDriver;
    }

    @Override
    public ApiResponse deleteDriver(Long id) throws ResourceNotFoundException {
        Optional<Driver> optional = driverRepository.findById(id);
        if (optional.isPresent()) {
            driverRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException();
        }
        return new ApiResponse(Boolean.TRUE, "Account successfully deleted.");
    }
}

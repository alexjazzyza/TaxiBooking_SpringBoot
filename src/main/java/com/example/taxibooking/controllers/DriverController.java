package com.example.taxibooking.controllers;


import com.example.taxibooking.mapstructs.dtos.DriverGetDto;
import com.example.taxibooking.mapstructs.dtos.DriverPostDto;
import com.example.taxibooking.mapstructs.mappers.IDriverMapper;
import com.example.taxibooking.models.users.Driver;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.services.implementations.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/driver/")
public class DriverController {

    private DriverService driverService;

    private IDriverMapper driverMapper;

    public DriverController(DriverService driverService, IDriverMapper driverMapper) {
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @PostMapping()
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody DriverPostDto driver){
        return new ResponseEntity<>(driverService.saveDriver(driverMapper.dtoToModel(driver)), HttpStatus.CREATED);
    }

    @GetMapping("{id}/")
    public ResponseEntity<DriverGetDto> getDriverById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(driverMapper.modelToDto(driverService.getDriverById(id)), HttpStatus.OK);
    }

    @PutMapping("{id}/")
    public ResponseEntity<Driver> updateDriver(@PathVariable(value = "id") Long id, @Valid @RequestBody DriverPostDto driver){
        return new ResponseEntity<>(driverService.updateDriver(id, driverMapper.dtoToModel(driver)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<ApiResponse> DeleteCustomer(@PathVariable(value = "id") Long id){

        ApiResponse apiResponse = driverService.deleteDriver(id);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

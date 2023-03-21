package com.example.taxibooking.controllers;

import com.example.taxibooking.mapstructs.dtos.CustomerGetDto;
import com.example.taxibooking.mapstructs.dtos.CustomerPostDto;
import com.example.taxibooking.mapstructs.mappers.ICustomerMapper;
import com.example.taxibooking.models.CreditCard;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.services.ICustomerService;
import com.example.taxibooking.services.implementations.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customers/")
public class CustomerController {

    private ICustomerService customerService;

    private ICustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, ICustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }


    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerPostDto customer){
        return new ResponseEntity<>(customerService.saveCustomer(customerMapper.dtoToModel(customer)), HttpStatus.CREATED);
    }

    @GetMapping("{id}/")
    public ResponseEntity<CustomerGetDto> getCustomerById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(customerMapper.modelToDto(customerService.getCustomerById(id)), HttpStatus.OK);
    }

    @PutMapping("{id}/")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long id, @Valid @RequestBody CustomerPostDto customer){
        return new ResponseEntity<>(customerService.updateCustomer(id, customerMapper.dtoToModel(customer)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<ApiResponse> DeleteCustomer(@PathVariable(value = "id") Long id){

        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/add-credit-card")
    public String createCreditCard(@RequestBody CreditCard creditCard){
        customerService.saveCreditCard(creditCard);
        return"Success";
    }
}

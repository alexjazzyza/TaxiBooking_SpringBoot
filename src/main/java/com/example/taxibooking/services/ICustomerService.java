package com.example.taxibooking.services;

import com.example.taxibooking.models.CreditCard;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.payloads.ApiResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public interface ICustomerService {

    Customer saveCustomer(Customer customer);
    Customer getCustomerById(Long id) throws ResourceNotFoundException, IllegalArgumentException;
    Customer updateCustomer(Long id, Customer customer) throws ResourceNotFoundException, IllegalArgumentException;
    ApiResponse deleteCustomer(Long id) throws ResourceNotFoundException, IllegalArgumentException;

    CreditCard saveCreditCard(CreditCard creditCard);
}

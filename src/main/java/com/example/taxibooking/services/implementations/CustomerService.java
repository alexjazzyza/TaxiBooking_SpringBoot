package com.example.taxibooking.services.implementations;

import com.example.taxibooking.models.CreditCard;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.repositories.ICreditCardRepository;
import com.example.taxibooking.repositories.ICustomerRepository;
import com.example.taxibooking.services.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    private ICustomerRepository customerRepository;

    private ICreditCardRepository creditCardRepository;

    public CustomerService(ICustomerRepository customerRepository, ICreditCardRepository creditCardRepository) {
        this.customerRepository = customerRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id){
        Customer customer;
        if (id != null) {
            Optional<Customer> optional = customerRepository.findById(id);
            if (optional.isPresent()) {
                customer = optional.get();
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return customer;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer){
        Customer updateCustomer;
        if (id != null) {
            Optional<Customer> optional = customerRepository.findById(id);
            if (optional.isPresent()) {
                updateCustomer = optional.get();
                updateCustomer.setFirstName(customer.getFirstName());
                updateCustomer.setLastName(customer.getLastName());
                updateCustomer.setEmail(customer.getEmail());
                updateCustomer.setPhoneNumber(customer.getPhoneNumber());
                updateCustomer.setPassword(customer.getPassword());
                updateCustomer.setPicture(customer.getPicture());
                customerRepository.save(updateCustomer);
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return updateCustomer;
    }

    @Override
    public ApiResponse deleteCustomer(Long id) {
        if (id != null)
        {
            Optional<Customer> optional = customerRepository.findById(id);
            if (optional.isPresent()) {
                customerRepository.deleteById(id);
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return new ApiResponse(Boolean.TRUE, "Account has been successfully deleted.");
    }

    public CreditCard saveCreditCard(CreditCard creditCard){
        if (creditCard.getId() != null) {
            Optional<CreditCard> optional = creditCardRepository.findById(creditCard.getId());
            if (optional.isPresent()) {
                creditCardRepository.save(creditCard);
            }
        }
        creditCardRepository.save(creditCard);
        return creditCard;
    }
}

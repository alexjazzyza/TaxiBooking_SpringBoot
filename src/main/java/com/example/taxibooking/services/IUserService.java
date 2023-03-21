package com.example.taxibooking.services;

import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.User;
import com.example.taxibooking.payloads.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

public interface IUserService {

    User getUserById(Long id) throws ResourceNotFoundException, IllegalArgumentException;
    User updateUser(Long id, User user) throws ResourceNotFoundException, IllegalArgumentException;
    ApiResponse deleteUser(Long id) throws ResourceNotFoundException, IllegalArgumentException;
}

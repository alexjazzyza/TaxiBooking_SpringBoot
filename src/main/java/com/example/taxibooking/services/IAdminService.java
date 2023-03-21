package com.example.taxibooking.services;

import com.example.taxibooking.models.users.Admin;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.payloads.ApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public interface IAdminService {

    Admin saveAdmin(Admin admin);
    Admin getAdminById(Long id) throws ResourceNotFoundException, IllegalArgumentException;
    Admin updateAdmin(Long id, Admin admin) throws ResourceNotFoundException, IllegalArgumentException;
    ApiResponse deleteAdmin(Long id) throws ResourceNotFoundException, IllegalArgumentException;
}

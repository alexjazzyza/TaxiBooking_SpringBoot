package com.example.taxibooking.services.implementations;

import com.example.taxibooking.models.users.Admin;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.repositories.IAdminRepository;
import com.example.taxibooking.services.IAdminService;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AdminService implements IAdminService {

    private IAdminRepository adminRepository;

    public AdminService(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Long id) throws ResourceNotFoundException, IllegalArgumentException {
        Admin admin;
        if (id != null) {
            Optional<Admin> optional = adminRepository.findById(id);
            if (optional.isPresent()) {
                admin = optional.get();
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return admin;
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) throws ResourceNotFoundException, IllegalArgumentException {
        Admin updateAdmin;
        if (id != null) {
            Optional<Admin> optional = adminRepository.findById(id);
            if (optional.isPresent()) {
                updateAdmin = optional.get();
                updateAdmin.setFirstName(admin.getFirstName());
                updateAdmin.setLastName(admin.getLastName());
                updateAdmin.setEmail(admin.getEmail());
                updateAdmin.setPhoneNumber(admin.getPhoneNumber());
                updateAdmin.setPassword(admin.getPassword());
                updateAdmin.setPicture(admin.getPicture());
                adminRepository.save(updateAdmin);
            } else {
                throw new ResourceNotFoundException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return updateAdmin;
    }

    @Override
    public ApiResponse deleteAdmin(Long id) throws ResourceNotFoundException, IllegalArgumentException {
        if (id != null)
        {
            Optional<Admin> optional = adminRepository.findById(id);
            if (optional.isPresent()) {
                adminRepository.deleteById(id);
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
}

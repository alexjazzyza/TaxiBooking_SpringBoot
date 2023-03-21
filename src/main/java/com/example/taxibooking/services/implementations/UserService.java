package com.example.taxibooking.services.implementations;

import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.User;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.repositories.IUserRepository;
import com.example.taxibooking.services.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) throws ResourceNotFoundException, IllegalArgumentException {
        User user;
        if (id != null) {
            Optional<User> optional = userRepository.findById(id);
            if (optional.isPresent()) {
                user = optional.get();
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return user;
    }

    @Override
    public User updateUser(Long id, User user) throws ResourceNotFoundException, IllegalArgumentException {
        User updateUser;
        if (id != null) {
            Optional<User> optional = userRepository.findById(id);
            if (optional.isPresent()) {
                updateUser = optional.get();
                updateUser.setFirstName(user.getFirstName());
                updateUser.setLastName(user.getLastName());
                updateUser.setEmail(user.getEmail());
                updateUser.setPhoneNumber(user.getPhoneNumber());
                updateUser.setPassword(user.getPassword());
                updateUser.setPicture(user.getPicture());
                userRepository.save(updateUser);
            }
            else {
                throw new ResourceNotFoundException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return updateUser;
    }

    @Override
    public ApiResponse deleteUser(Long id) throws ResourceNotFoundException, IllegalArgumentException {
        if (id != null)
        {
            Optional<User> optional = userRepository.findById(id);
            if (optional.isPresent()) {
                userRepository.deleteById(id);
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

package com.example.taxibooking.services.implementations;

import com.example.taxibooking.models.users.Admin;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.Driver;
import com.example.taxibooking.models.users.User;
import com.example.taxibooking.repositories.IAdminRepository;
import com.example.taxibooking.repositories.ICustomerRepository;
import com.example.taxibooking.repositories.IDriverRepository;
import com.example.taxibooking.repositories.IUserRepository;
import com.example.taxibooking.security.UserPrincipal;
import com.example.taxibooking.services.ICustomUserDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements ICustomUserDetailsService, UserDetailsService {

    private final IUserRepository userRepository;
    private ICustomerRepository customerRepository;
    private IDriverRepository driverRepository;
    private IAdminRepository adminRepository;

    public CustomUserDetailsService(IUserRepository userRepository, ICustomerRepository customerRepository, IDriverRepository driverRepository, IAdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumberOrEmail) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByPhoneNumberOrEmail(phoneNumberOrEmail, phoneNumberOrEmail);
        if (user.isPresent()) {
            return UserPrincipal.create(user.get());
        }
        throw new UsernameNotFoundException(String.format("User not found with this phone number or email: %s", phoneNumberOrEmail));
    }

    @Override
    public UserDetails loadUserById(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return UserPrincipal.create(user.get());
        }
        throw new UsernameNotFoundException(String.format("User not found with id: %s", id));
    }
}

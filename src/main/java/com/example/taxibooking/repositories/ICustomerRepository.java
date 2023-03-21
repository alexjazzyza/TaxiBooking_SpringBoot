package com.example.taxibooking.repositories;

import com.example.taxibooking.models.users.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhoneNumber(@NotBlank String username);

    Boolean existsByEmail(@NotBlank String email);

    Optional<Customer> findByPhoneNumberOrEmail(String username, String email);
}

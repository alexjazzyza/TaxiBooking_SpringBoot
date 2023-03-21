package com.example.taxibooking.repositories;

import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.Driver;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDriverRepository extends JpaRepository<Driver, Long> {

    Boolean existsByPhoneNumber(@NotBlank String username);

    Boolean existsByEmail(@NotBlank String email);

    Optional<Driver> findByPhoneNumberOrEmail(String username, String email);
}

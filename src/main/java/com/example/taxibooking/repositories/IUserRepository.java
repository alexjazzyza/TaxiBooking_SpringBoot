package com.example.taxibooking.repositories;

import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Boolean existsByPhoneNumber(@NotBlank String phoneNumber);

    Boolean existsByEmail(@NotBlank String email);

    Optional<User> findByPhoneNumberOrEmail(String phoneNumber, String email);
}

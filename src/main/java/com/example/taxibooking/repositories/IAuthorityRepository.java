package com.example.taxibooking.repositories;

import com.example.taxibooking.models.role.ERoleName;
import com.example.taxibooking.models.role.Authority;
import com.example.taxibooking.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(ERoleName name);
}

package com.example.taxibooking.repositories;

import com.example.taxibooking.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditCardRepository extends JpaRepository<CreditCard, Long> {
}

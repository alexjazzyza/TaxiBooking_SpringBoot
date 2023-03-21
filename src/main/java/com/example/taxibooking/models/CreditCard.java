package com.example.taxibooking.models;

import com.example.taxibooking.models.users.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "t_creditCards")
public class CreditCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String expirationDate;

    @Column(length = 3, nullable = false)
    private String cvc;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creditCard")
    private Set<Payment> payments = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Version
    @Column(name = "version", nullable = false)
    private int vrs;
}

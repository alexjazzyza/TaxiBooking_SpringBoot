package com.example.taxibooking.models;

import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.Driver;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "t_reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver", nullable = false)
    private Driver driver;

    @Column(length = 100)
    private String departureAddress;

    @Column(length = 100)
    private String destinationAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDateTime;

    private Integer estimatedTime;

    private Float Price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment", referencedColumnName = "id")
    private Payment payment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Version
    @Column(name = "version", nullable = false)
    private int vrs;
}

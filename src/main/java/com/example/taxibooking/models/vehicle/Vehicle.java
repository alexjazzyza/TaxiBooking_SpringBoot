package com.example.taxibooking.models.vehicle;

import com.example.taxibooking.models.users.Driver;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "t_vehicle")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private EVehicleType vehicleType;

    @Column(length = 30)
    private String brand;

    @Column(length = 30)
    private String model;

    @Column(length = 30)
    private String color;

    @Column(length = 10)
    private String plateNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;
}

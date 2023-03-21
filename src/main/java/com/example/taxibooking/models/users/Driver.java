package com.example.taxibooking.models.users;

import com.example.taxibooking.models.Reservation;
import com.example.taxibooking.models.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("driver")
public class Driver extends User implements Serializable {

    @Column(length = 40)
    private String licenseNumber;

    private Float evaluationScore;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    private List<Vehicle> vehicles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    private Set<Reservation> reservations = new HashSet<>();

    public Driver(String firstName, String lastName, String phoneNUmber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNUmber;
        this.email = email;
        this.password = password;
    }
}

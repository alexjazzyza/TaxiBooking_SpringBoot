package com.example.taxibooking.models.users;

import com.example.taxibooking.models.CreditCard;
import com.example.taxibooking.models.Reservation;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("customer")
//@Table(name = "t_customers")
public class Customer extends User implements Serializable {

    public Customer(String firstName, String lastName, String phoneNUmber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNUmber;
        this.email = email;
        this.password = password;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<CreditCard> creditCards = new HashSet<>();

}

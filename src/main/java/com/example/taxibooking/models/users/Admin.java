package com.example.taxibooking.models.users;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("admin")
//@Table(name = "t_admins")
public class Admin extends User implements Serializable {

    public Admin(String firstName, String lastName, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

}

package com.example.taxibooking.models.users;

import com.example.taxibooking.models.role.Authority;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Table(name = "t_users")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(length = 50, nullable = false)
    protected String firstName;

    @Column(length = 50, nullable = false)
    protected String lastName;

    @Column(length = 100, nullable = false)
    protected String email;

    @Column(length = 10, nullable = false)
    protected String phoneNumber;

    @Column(nullable = false)
    protected String password;

    protected String picture;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected List<Authority> authorities;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationDateTime = new Date();

    @Version
    @Column(nullable = false)
    protected int version;

    public User(String firstName, String lastName, String email, String phoneNumber, String password, List<Authority> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authorities = authorities;
    }
}

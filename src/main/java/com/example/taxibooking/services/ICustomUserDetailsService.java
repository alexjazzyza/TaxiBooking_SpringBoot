package com.example.taxibooking.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICustomUserDetailsService {

    UserDetails loadUserByUsername(String phoneNumberOrEmail) throws UsernameNotFoundException;

    UserDetails loadUserById(Long id);
}

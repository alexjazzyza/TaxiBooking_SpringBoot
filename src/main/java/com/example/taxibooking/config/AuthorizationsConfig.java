package com.example.taxibooking.config;

import com.example.taxibooking.exceptions.AppException;
import com.example.taxibooking.models.role.Authority;
import com.example.taxibooking.models.role.ERoleName;
import com.example.taxibooking.models.users.Admin;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.Driver;
import com.example.taxibooking.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class AuthorizationsConfig implements CommandLineRunner {

    private final IAuthorityRepository authorityRepository;
    private final TransactionTemplate transactionTemplate;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                Authority adminAuthority = null;
                Authority customerAuthority = null;
                Authority driverAuthority = null;

                String USER_ROLE_NOT_SET = "User role not set";

                if (Boolean.FALSE.equals(authorityRepository.findByName(ERoleName.ROLE_ADMIN).isPresent()))
                {
                    adminAuthority = authorityRepository.save(new Authority(ERoleName.ROLE_ADMIN));
                }
                if (Boolean.FALSE.equals(authorityRepository.findByName(ERoleName.ROLE_CUSTOMER).isPresent())) {
                    customerAuthority = authorityRepository.save(new Authority(ERoleName.ROLE_CUSTOMER));
                }
                if (Boolean.FALSE.equals(authorityRepository.findByName(ERoleName.ROLE_DRIVER).isPresent())) {
                    driverAuthority = authorityRepository.save(new Authority(ERoleName.ROLE_DRIVER));
                }

                if (Boolean.FALSE.equals(userRepository.findByPhoneNumberOrEmail("0660046505", "alexaziza@hotmail.com").isPresent())) {
                    Admin admin = new Admin("admin", "admin", "0660046505", "alexaziza@hotmail.com", passwordEncoder.encode("7531594862aZ#"));
                    List<Authority> authorities = new ArrayList<>();
                    authorities.add(authorityRepository.findByName(ERoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
                    admin.setAuthorities(authorities);

                    userRepository.save(admin);
                }


                if (Boolean.FALSE.equals(userRepository.findByPhoneNumberOrEmail("0000000000", "customer@customer.com").isPresent())) {
                    Customer customer = new Customer("Customer", "Customer", "0000000000", "customer@customer.com", passwordEncoder.encode("789632145aA@"));
                    List<Authority> authorities = new ArrayList<>();
                    authorities.add(authorityRepository.findByName(ERoleName.ROLE_CUSTOMER)
                            .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
                    customer.setAuthorities(authorities);

                    userRepository.save(customer);
                }
            }
        });
    }
}

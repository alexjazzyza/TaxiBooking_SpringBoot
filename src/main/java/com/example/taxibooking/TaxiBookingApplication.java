package com.example.taxibooking;

import com.example.taxibooking.config.RsaKeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(RsaKeysConfig.class)
@EnableMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(enableDefaultTransactions = false)
public class TaxiBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaxiBookingApplication.class, args);
    }

}

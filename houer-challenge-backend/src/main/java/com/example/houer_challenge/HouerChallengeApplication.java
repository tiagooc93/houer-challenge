package com.example.houer_challenge;

import com.example.houer_challenge.controller.AuthenticationController;
import com.example.houer_challenge.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HouerChallengeApplication  implements CommandLineRunner {

    @Autowired
    AuthenticationController authenticationController;

    public static void main(String[] args) {
        SpringApplication.run(HouerChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        authenticationController.register(new RegisterDTO("test@test.com","123"));
    }
}


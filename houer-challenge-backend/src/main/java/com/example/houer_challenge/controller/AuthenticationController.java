package com.example.houer_challenge.controller;

import com.example.houer_challenge.dto.LoginDTO;
import com.example.houer_challenge.dto.LoginResponseDTO;
import com.example.houer_challenge.model.UserInfo;
import com.example.houer_challenge.repository.UserInfoRepository;
import com.example.houer_challenge.service.TokenService;
import com.example.houer_challenge.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private UserInfoService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO body){
        log.info("POST /auth/login");

        UserInfo user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getEmail(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO body){
        log.info("POST /auth/register");

        Optional<UserInfo> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            UserInfo newUser = new UserInfo();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            userService.addUser(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new LoginResponseDTO(newUser.getEmail(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
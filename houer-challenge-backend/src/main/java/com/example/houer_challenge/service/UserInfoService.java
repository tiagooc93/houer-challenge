package com.example.houer_challenge.service;

import com.example.houer_challenge.model.UserInfo;
import com.example.houer_challenge.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInfoService {

    @Autowired
    UserInfoRepository usersRepository;

    public UserInfo addUser(UserInfo user){
        log.info("Adding new user: {}", user.toString());

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use !");
        }


        user = usersRepository.save(user);

        return user;
    }

}
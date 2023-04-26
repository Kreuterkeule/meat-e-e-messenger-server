package com.kreuterkeule.meateemessengerserver.controllers;

import com.kreuterkeule.meateemessengerserver.dto.RegisterDto;
import com.kreuterkeule.meateemessengerserver.entities.UserEntity;
import com.kreuterkeule.meateemessengerserver.repositories.UserRepository;
import com.kreuterkeule.meateemessengerserver.service.UniqueTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserRepository userRepository;
    private UniqueTokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, UniqueTokenProvider tokenProvider, PasswordEncoder getPasswordEncoder, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username) != null) {
            return new ResponseEntity<>(new UserEntity("username_taken_user",
                    "username_taken_password",
                    "",
                    ""),
                    HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.save(new UserEntity(registerDto.username, passwordEncoder.encode(registerDto.password), tokenProvider.generateToken(), registerDto.pubKey));
        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

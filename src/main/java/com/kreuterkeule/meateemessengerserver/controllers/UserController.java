package com.kreuterkeule.meateemessengerserver.controllers;

import com.kreuterkeule.meateemessengerserver.entities.UserEntity;
import com.kreuterkeule.meateemessengerserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("searchPublicUser")
    public ResponseEntity<List<UserEntity>> searchPublicUser(@RequestParam("query") String query) {
        return new ResponseEntity<>(userRepository.searchPublicUsers(query), HttpStatus.OK); // only returns public users
        // on default users aren't public, but they can change that at ./changePublic ? state = String[public/notPublic]
    }

    @GetMapping("changePublic")
    public ResponseEntity<UserEntity> changePublic(@RequestParam("state") String state) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String clientName = authentication.getName();
        UserEntity client = userRepository.findByUsername(clientName);
        if (state.equals("public")) {
            client.setPublic(true);
            userRepository.save(client);
        } else if (state.equals("notPublic")) {
            client.setPublic(false);
            userRepository.save(client);
        } else {
            System.out.println("[ERROR] no valid state for changePublic() in UserController");
            return new ResponseEntity<>(client, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

}

package com.example.demosecurity.controllers.rest;


import com.example.demosecurity.model.User;
import com.example.demosecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/info")
//    public ResponseEntity<User> user() {
//        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
//    }

    @GetMapping
    public User getUser(Principal principal){
        User user = userService.findUserByUsername(principal.getName());
        return user;
    }
}

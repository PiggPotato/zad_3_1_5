package com.example.demosecurity.controllers;


import com.example.demosecurity.model.Role;
import com.example.demosecurity.model.User;
import com.example.demosecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @GetMapping("/about")
//    public String aboutUser(Model model, Principal principal) {
//        User user = userService.findUserByUsername(principal.getName());
//        String roles = user.getCleanRoles();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roles);
//        return "user/about";
//    }

    @GetMapping("/about")
    public String aboutUser() {
        return "user/about";
    }


}

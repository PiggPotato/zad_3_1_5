package com.example.demosecurity.controllers.rest;


import com.example.demosecurity.exceptions.UserNotFoundException;
import com.example.demosecurity.model.User;
import com.example.demosecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/all_users")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public User addNewUser(@RequestBody User user) {
        userService.createNewUser(user);
        return user;
    }


    @PatchMapping()
    public User editUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("There is no user with ID = %d in Database", id));
        }
        return user;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("There is no user whits ID = %d in Database", id));
        }
        userService.deleteUser(id);
        return String.format("User with ID = %d was deleted", id);
    }


}

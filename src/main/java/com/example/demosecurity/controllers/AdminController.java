package com.example.demosecurity.controllers;

import com.example.demosecurity.model.Role;
import com.example.demosecurity.model.User;
import com.example.demosecurity.service.RoleService;
import com.example.demosecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    final UserService userService;
    final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/all_users")
    public String allUsers(Model model, Principal principal, @ModelAttribute("user") User user) {
        User actualUser = userService.findUserByUsername(principal.getName());
        model.addAttribute("actual_user", actualUser);

        String cleanRoles = actualUser.getCleanRoles();
        model.addAttribute("clean_roles", cleanRoles);

        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        List<User> users = userService.getAllUsers();
        model.addAttribute("all_users", users);

        model.addAttribute("newUser", new User());
        return "admin/all_users";
    }

    @PostMapping
    public String addNewUserPOST(@ModelAttribute("user") User user){
        userService.createNewUser(user);
        return "redirect:/admin/all_users";
    }

    @PatchMapping("/all_users/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/all_users";
    }

    @DeleteMapping("/all_users/{id}")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin/all_users";
    }

}

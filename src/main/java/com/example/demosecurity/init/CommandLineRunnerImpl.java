package com.example.demosecurity.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demosecurity.model.Role;
import com.example.demosecurity.model.User;
import com.example.demosecurity.repositories.RoleRepository;
import com.example.demosecurity.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // @Autowired
    public CommandLineRunnerImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void run(String... arg) throws Exception {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> adminRoles = new HashSet<>();
        Set<Role> userRoles = new HashSet<>();
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);
        userRoles.add(roleUser);


        // пользователи Admin  и User
        User userAdmin = new User("admin", "$2a$10$d5nr2mbGlAS.irXIgeT3.uDIcRd9lYcApqefhw8XFOfg6Uzc0Xhsu", 25, adminRoles);
        User userUser = new User("user", "$2a$10$urBckJui4ANeLqQ4FFro3OgDr3HtOJYhGaOM1xWrZvseV3qfMXi3u", 24, userRoles);
        System.out.println(userAdmin);
        userRepository.save(userAdmin);
        System.out.println(userUser);
        userRepository.save(userUser);

    }

}

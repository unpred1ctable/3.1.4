package ru.kata.spring.boot_security.demo.Util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.Entities.Role;
import ru.kata.spring.boot_security.demo.Entities.User;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Initializer {
    private final UserService userService;
    private final RoleService roleService;

    public Initializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void createRolesAndBasicUsers() {
        if (roleService.getAllRoles().isEmpty() || userService.getAllUsers().isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            Set<Role> adminSet = new HashSet<>();
            Set<Role> userSet = new HashSet<>();

            roleService.addRole(roleAdmin);
            roleService.addRole(roleUser);

            adminSet.add(roleAdmin);
            adminSet.add(roleUser);
            userSet.add(roleUser);

            /*
            admin - username: admin
                    password: admin

            user - username: user
                   password: user
             */

            User admin = new User("admin", "admin's name", "admin's surname",
                    "admin", 25, "admin@admin.com", adminSet);
            User user = new User("user", "user's name", "user's surname",
                    "user", 45, "user@user.com", userSet);

            userService.add(user);
            userService.add(admin);
        }
    }
}
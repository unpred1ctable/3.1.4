package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/user")
    public String myPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        return "user";
    }

}

package ru.kata.spring.boot_security.demo.Controller;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Entities.User;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.Util.UserValidator;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    private final UserDetailsService userDetailsService;
    public AdminController(UserService userService, RoleService roleService,
                           UserValidator userValidator, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.userDetailsService = userDetailsService;
    }
    @GetMapping("")
    public String index(@ModelAttribute("user") User user, Principal principal, Model model) {
        model.addAttribute("myUser", userDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/index";
    }
    @PostMapping("/")
    public String save(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        userService.setUserRoles(user);
        userService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        userService.setUserRoles(user);
        userService.update(user);
        return "redirect:/admin";
    }

    //--------Delete-----------
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}

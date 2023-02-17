package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.servise.MyRolesCheck;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("")
    public String index(Principal principal, Model model) {
        model.addAttribute("userAuthorized", userService.findByName(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("MyRolesCheck", new MyRolesCheck());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user_get", userService.findById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user_new") User user, Model model, Principal principal) {
        model.addAttribute("roles", roleService.findAll());
        Role user_role = roleService.findAll().get(0);
        Role admin_role = roleService.findAll().get(1);
        model.addAttribute("admin_role", admin_role);
        model.addAttribute("user_role", user_role);
        model.addAttribute("userAuthorized", userService.findByName(principal.getName()));
        model.addAttribute("MyRolesCheck", new MyRolesCheck());
        return "admin/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute("user_new") @Valid User user, BindingResult bindingRequest
            , @ModelAttribute("MyRolesCheck") MyRolesCheck myRolesCheck) {
        if (bindingRequest.hasErrors()) return "admin/new";
        if (myRolesCheck.getUserState()) user.setRole(roleService.findById(1));
        if (myRolesCheck.getAdminState()) user.setRole(roleService.findById(2));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("userr", userService.findById(id));
        MyRolesCheck myRolesCheck = new MyRolesCheck();
        if (userService.findById(id).getRole().contains(roleService.findById(1))) myRolesCheck.setUserState(true);
        if (userService.findById(id).getRole().contains(roleService.findById(2))) myRolesCheck.setAdminState(true);
        model.addAttribute("MyRolesCheck", myRolesCheck);
        return "admin/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("userr") @Valid User user, BindingResult bindingRequest,
                         @PathVariable("id") long id, @ModelAttribute("MyRolesCheck") MyRolesCheck myRolesCheck) {
        if (bindingRequest.hasErrors()) return null;
        if (myRolesCheck.getUserState()) user.setRole(roleService.findById(1));
        if (myRolesCheck.getAdminState()) user.setRole(roleService.findById(2));
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}

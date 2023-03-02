package ru.kata.spring.boot_security.demo.Util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.Entities.User;
import ru.kata.spring.boot_security.demo.Service.UserService;

@Component
public class UserValidator implements Validator {
    private UserService userService;
    public UserValidator(UserService userService) {
        this.userService = userService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.getUserByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "User already exists");
        }
    }
}

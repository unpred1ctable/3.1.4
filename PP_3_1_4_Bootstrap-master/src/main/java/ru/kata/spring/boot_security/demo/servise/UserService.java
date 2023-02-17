package ru.kata.spring.boot_security.demo.servise;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(long id);

    User findByName(String name);

    void save(User user);

    void update(long id, User updUser);

    void delete(long id);
}
package ru.kata.spring.boot_security.demo.Dao;

import ru.kata.spring.boot_security.demo.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    void delete(Long id);

    void update(User user);
}

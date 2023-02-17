package ru.kata.spring.boot_security.demo.servise;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    Role findById(long id);

    void save(Role role);

    void update(long id, Role updRole);

    void delete(long id);

    List<Role> findAll();

    String getAuthority(Role role);
}

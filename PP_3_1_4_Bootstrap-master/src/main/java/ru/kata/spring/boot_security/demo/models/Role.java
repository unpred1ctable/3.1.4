package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

    @ManyToMany(mappedBy = "role")
    private Set<User> users;

    public Role(){}

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    @Override
    public String getAuthority() {
        return role.substring(5);
    }
}

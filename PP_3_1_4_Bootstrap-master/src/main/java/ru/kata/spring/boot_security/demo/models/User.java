package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String lastName;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)//(cascade = CascadeType.ALL)
    private Set<Role> role = new HashSet<>();

    public User() {
    }

    public User(String username, String lastName) {
        this.username = username;
        this.lastName = lastName;
    }

    public User(String password, String username, String lastName) {
        this(username, lastName);
        this.password = password;
    }

    public User(String password, String name, String lastName, Role role) {
        this(password, name, lastName);
        this.setRole(role);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().stream().map(a-> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role.add(role);
    }

    public boolean isAdmin() {
        final boolean[] i = {false};
        role.forEach(a -> {
            if (a.getRole().equals("ROLE_ADMIN")) {
                i[0] = true;
            }
        });
        return i[0];
    }

    public void deleteRole(Role role) {
        this.role.remove(role);
    }
}

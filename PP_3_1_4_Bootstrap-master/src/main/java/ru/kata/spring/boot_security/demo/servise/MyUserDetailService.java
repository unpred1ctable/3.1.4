package ru.kata.spring.boot_security.demo.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {


    private final UsersRepository usersRepository;

    @Autowired
    public MyUserDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        String passAdapted = new BCryptPasswordEncoder().encode(user.getPassword());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), passAdapted,
                roleToAuthorities(user));
    }

    @Transactional
    Collection<? extends GrantedAuthority> roleToAuthorities(User user) {

        List<SimpleGrantedAuthority> lst = user.getRole().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toList());
        lst.add(new SimpleGrantedAuthority(String.valueOf(user.getId())));
        return lst;
    }
}

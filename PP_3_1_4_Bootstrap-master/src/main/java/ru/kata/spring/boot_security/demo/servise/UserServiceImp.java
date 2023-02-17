package ru.kata.spring.boot_security.demo.servise;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        Optional<User> user = usersRepository.findById(id);
        return user.orElse(null);
    }

    @Transactional(readOnly = true)
    public User findByName(String name) {
        try {
            return usersRepository.findByUsername(name);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(long id, User updUser) {
        updUser.setId(id);
        usersRepository.save(updUser);
    }

    @Transactional
    public void delete(long id) {
        usersRepository.deleteById(id);
    }

}

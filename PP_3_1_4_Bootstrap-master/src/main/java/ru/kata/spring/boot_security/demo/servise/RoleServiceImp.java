package ru.kata.spring.boot_security.demo.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public String getAuthority(Role role) {
        return role.getAuthority();
    }

    public Role findById(long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void update(long id, Role updRole) {
        updRole.setId(id);
        roleRepository.save(updRole);
    }

    @Transactional
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

}

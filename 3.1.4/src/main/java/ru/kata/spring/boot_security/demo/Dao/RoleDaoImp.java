package ru.kata.spring.boot_security.demo.Dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.Entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Role addRole(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public Set<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r from Role r", Role.class).getResultList().stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<Role> findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where name = :name", Role.class)
                .setParameter("name", name);
        return query.getResultList().stream().findFirst();

    }
}

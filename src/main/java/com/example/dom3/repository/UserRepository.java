package com.example.dom3.repository;

import com.example.dom3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
//@NoRepositoryBean
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByName(String name);
    public User findByEmail(String email);
}

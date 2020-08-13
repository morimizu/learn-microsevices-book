package com.example.learnmicrosevicesbook.multiplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.learnmicrosevicesbook.multiplication.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}

package com.example.learnmicrosevicesbook.multiplication.repository;

import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MultiplicationAttemptRepository extends JpaRepository<MultiplicationResultAttempt,Long> {
    List<MultiplicationResultAttempt> findTop5ByUserUsernameOrderByIdDesc(String userUsername);
}

package com.example.learnmicrosevicesbook.multiplication.repository;

import com.example.learnmicrosevicesbook.multiplication.entity.Multiplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MultiplicationRepository extends JpaRepository<Multiplication,Long> {
    Optional<Multiplication> findByFactorAAndFactorB(int factorA,int factorB);
}

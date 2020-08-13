package com.example.learnmicrosevicesbook.multiplication.service;

import com.example.learnmicrosevicesbook.multiplication.entity.Multiplication;
import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;

import java.util.List;

/**
 * creates a multiplication with factors between 11 and 99
 * @return a @Multiplication object with random factors
 */
public interface MultiplicationService {
    /**
     * Generates a random {@link Multiplication} object.
     *
     * @return a multiplication of randomly generated numbers
     */
    Multiplication createRandomMultiplication();
    /**
     * @return true if the attempt matches the result of the
     *         multiplication, false otherwise.
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    List<MultiplicationResultAttempt> getStatsForUser(String username);
}

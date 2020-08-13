package com.example.learnmicrosevicesbook.multiplication.entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication} .
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class MultiplicationResultAttempt {
    @Id
    @GeneratedValue
    @Column(name = "attempt_id")
    private Long id;
    @ManyToOne( cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private final User user;
    @ManyToOne( cascade = CascadeType.PERSIST)
    @JoinColumn(name = "multiplication_id")
    private final Multiplication multiplication;
    private final int resultAttempt;
    private final boolean correct;
    // Empty constructor for JSON (de)serialization

    MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }
}
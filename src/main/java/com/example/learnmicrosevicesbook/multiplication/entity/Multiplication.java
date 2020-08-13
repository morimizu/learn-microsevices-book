package com.example.learnmicrosevicesbook.multiplication.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {
    private final int factorA;
    private final int factorB;
    @Id
    @GeneratedValue
    @Column(name = "multiplication_id")
    private long id;

    Multiplication(){
        this(0,0);
    }
}

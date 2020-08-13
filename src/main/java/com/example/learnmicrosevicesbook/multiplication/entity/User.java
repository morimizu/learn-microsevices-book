package com.example.learnmicrosevicesbook.multiplication.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long id;

    private final String username;

    protected User(){
        username = null;
    }
    public User(String username){
        this.username = username;
    }
}

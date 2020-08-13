package com.example.learnmicrosevicesbook.multiplication.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class RequestResponce {
    private final boolean correct;

    public RequestResponce(boolean correct){
        this.correct =correct;
    }
}

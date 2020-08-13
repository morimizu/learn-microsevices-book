package com.example.learnmicrosevicesbook.multiplication.controller;

import com.example.learnmicrosevicesbook.multiplication.entity.Multiplication;
import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;
import com.example.learnmicrosevicesbook.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multiplications")
public class MultiplicationController {
    private final MultiplicationService service;

    @Autowired
    public MultiplicationController(final MultiplicationService service){
        this.service = service;
    }

    @GetMapping("/random")
    public Multiplication getRandomMultiplication(){
        return service.createRandomMultiplication();
    }

}

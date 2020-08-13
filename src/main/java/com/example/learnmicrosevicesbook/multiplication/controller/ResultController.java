package com.example.learnmicrosevicesbook.multiplication.controller;

import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;
import com.example.learnmicrosevicesbook.multiplication.entity.RequestResponce;
import com.example.learnmicrosevicesbook.multiplication.service.MultiplicationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {
    private MultiplicationService service;

    @Autowired
    public ResultController(MultiplicationService service){
        this.service =service;
    }

    @GetMapping
    public ResponseEntity<List<MultiplicationResultAttempt>> getStats(@RequestParam String username){
       return ResponseEntity.ok(service.getStatsForUser(username));
    }

    @PostMapping()
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody() MultiplicationResultAttempt multiplicationResultAttempt) {
        boolean correct = service.checkAttempt(multiplicationResultAttempt);
        MultiplicationResultAttempt result = new MultiplicationResultAttempt(multiplicationResultAttempt.getUser(),
                multiplicationResultAttempt.getMultiplication(),
                multiplicationResultAttempt.getResultAttempt(),
                correct);
        return ResponseEntity.ok(result);
    }

}

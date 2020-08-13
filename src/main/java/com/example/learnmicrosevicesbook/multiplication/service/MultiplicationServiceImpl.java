package com.example.learnmicrosevicesbook.multiplication.service;

import com.example.learnmicrosevicesbook.multiplication.entity.Multiplication;
import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;
import com.example.learnmicrosevicesbook.multiplication.entity.User;
import com.example.learnmicrosevicesbook.multiplication.repository.MultiplicationAttemptRepository;
import com.example.learnmicrosevicesbook.multiplication.repository.MultiplicationRepository;
import com.example.learnmicrosevicesbook.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomNumberService rnd;
    private MultiplicationAttemptRepository repo;
    private UserRepository userRepo;
    private MultiplicationRepository multRepo;

    @Autowired
    public MultiplicationServiceImpl(RandomNumberService rnd,MultiplicationAttemptRepository repo,UserRepository userRepo,MultiplicationRepository multRepo)
    {
        this.rnd = rnd;
        this.repo =repo;
        this.userRepo = userRepo;
        this.multRepo = multRepo;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        return new Multiplication(rnd.generateRandomNumber(),rnd.generateRandomNumber());
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        Optional<User> user = userRepo.findByUsername(resultAttempt.getUser().getUsername());
        boolean correct = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA() * resultAttempt.getMultiplication().getFactorB();
        Optional<Multiplication> mult = multRepo.findByFactorAAndFactorB(resultAttempt.getMultiplication().getFactorA(),resultAttempt.getMultiplication().getFactorB());
        Assert.isTrue(!resultAttempt.isCorrect(),"You cannot send correct as true!");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                                                    user.orElse(resultAttempt.getUser()),
                                                    mult.orElse(resultAttempt.getMultiplication()),
                                                    resultAttempt.getResultAttempt(),
                                                    correct);

        repo.save(attempt);
        return correct;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String username) {
        return repo.findTop5ByUserUsernameOrderByIdDesc(username);
    }
}

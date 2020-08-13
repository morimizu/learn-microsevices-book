package com.example.learnmicrosevicesbook.multiplication.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNumberServiceImpl implements RandomNumberService{
    final static int MINIMUM_FACTOR = 11;
    final static int MAXIMUM_FACTOR = 99;
    @Override
    public int generateRandomNumber() {
        return new Random().nextInt((MAXIMUM_FACTOR-MINIMUM_FACTOR)+1)+MINIMUM_FACTOR;
    }
}

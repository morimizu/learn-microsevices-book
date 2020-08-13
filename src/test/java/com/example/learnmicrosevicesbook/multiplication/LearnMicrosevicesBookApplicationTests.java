package com.example.learnmicrosevicesbook.multiplication;

import com.example.learnmicrosevicesbook.multiplication.entity.Multiplication;
import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;
import com.example.learnmicrosevicesbook.multiplication.entity.User;
import com.example.learnmicrosevicesbook.multiplication.repository.MultiplicationAttemptRepository;
import com.example.learnmicrosevicesbook.multiplication.repository.MultiplicationRepository;
import com.example.learnmicrosevicesbook.multiplication.repository.UserRepository;
import com.example.learnmicrosevicesbook.multiplication.service.MultiplicationService;
import com.example.learnmicrosevicesbook.multiplication.service.MultiplicationServiceImpl;
import com.example.learnmicrosevicesbook.multiplication.service.RandomNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class LearnMicrosevicesBookApplicationTests {

    @Mock
    private RandomNumberService rnd;
    @Mock
    private UserRepository userRepo;
    @Mock
    private MultiplicationAttemptRepository attemptRepo;
    @Mock
    private MultiplicationRepository multRepo;

    private MultiplicationService service;

    @BeforeEach
    private void setup(){
        MockitoAnnotations.initMocks(this);
        service = new MultiplicationServiceImpl(rnd,attemptRepo,userRepo,multRepo);
    }

    @Test
    void createRandomMultiplicationTest() {
        given(rnd.generateRandomNumber()).willReturn(50,30);
        Multiplication result = service.createRandomMultiplication();
        assertThat(result.getFactorA()).isEqualTo(50);
        assertThat(result.getFactorB()).isEqualTo(30);
        assertThat(result.getFactorA() * result.getFactorB()).isEqualTo(1500);
    }
    @Test
    void checkCorrectAnswerTest(){
        Multiplication mult = new Multiplication(50,60);
        User user = new User("test_user");
        given(userRepo.findByUsername("test_user")).willReturn(Optional.empty());
        given(userRepo.save(any(User.class))).willReturn(user);
        given(multRepo.save(any(Multiplication.class))).willReturn(mult);
        given(multRepo.findByFactorAAndFactorB(any(Integer.class),any(Integer.class))).willReturn(Optional.empty());
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user,mult,3000,false);
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user,mult,3000,true);
        given(attemptRepo.save(any(MultiplicationResultAttempt.class))).willReturn(attempt);
        boolean result =  service.checkAttempt(attempt);
        assertThat(result).isTrue();
        verify(attemptRepo).save(resultAttempt);
    }
    @Test
    void checkIncorrectAnswerTest(){
        Multiplication mult = new Multiplication(50,60);
        User user = new User("test_user");
        given(userRepo.findByUsername("test_user")).willReturn(Optional.empty());
        given(userRepo.save(any(User.class))).willReturn(user);
        given(multRepo.save(any(Multiplication.class))).willReturn(mult);
        given(multRepo.findByFactorAAndFactorB(any(Integer.class),any(Integer.class))).willReturn(Optional.empty());
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user,mult,300,false);
        given(attemptRepo.save(any(MultiplicationResultAttempt.class))).willReturn(attempt);
        boolean result =  service.checkAttempt(attempt);
        assertThat(result).isFalse();
        verify(attemptRepo).save(attempt);
    }
    @Test
    void checkCorrectNotSet(){
        Multiplication mult = new Multiplication(50,60);
        User user = new User("test_user");
        given(userRepo.findByUsername("test_user")).willReturn(Optional.empty());
        given(userRepo.save(any(User.class))).willReturn(user);
        given(multRepo.save(any(Multiplication.class))).willReturn(mult);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user,mult,3000,true);
        given(attemptRepo.save(any(MultiplicationResultAttempt.class))).willReturn(attempt);
        assertThatThrownBy(()->{
            boolean result =  service.checkAttempt(attempt);
        });
        //assertThat(result).isTrue();
    }

    @Test
    void checkLastResults(){
        Multiplication mult = new Multiplication(50,60);
        User user = new User("test_user");
        given(userRepo.findByUsername("test_user")).willReturn(Optional.empty());
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user,mult,300,false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user,mult,30000,false);
        MultiplicationResultAttempt attempt3 = new MultiplicationResultAttempt(user,mult,3000,false);
        List<MultiplicationResultAttempt> attempts = Arrays.asList(attempt1,attempt2,attempt3);
        given(attemptRepo.findTop5ByUserUsernameOrderByIdDesc("test_user")).willReturn(attempts);

        List<MultiplicationResultAttempt> attemptsResult = service.getStatsForUser("test_user");
        assertThat(attemptsResult).isEqualTo(attempts);
    }

}

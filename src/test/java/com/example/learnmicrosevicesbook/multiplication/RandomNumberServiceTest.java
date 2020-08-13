package com.example.learnmicrosevicesbook.multiplication;

import com.example.learnmicrosevicesbook.multiplication.service.RandomNumberService;
import com.example.learnmicrosevicesbook.multiplication.service.RandomNumberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomNumberServiceTest {

    private RandomNumberService rnd;

    @BeforeEach
    private void setup(){
        rnd = new RandomNumberServiceImpl();
    }

    @Test
    public void checkNumberRange(){
        List<Integer> list = IntStream.range(0,1000)
                .map(i->rnd.generateRandomNumber())
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnlyElementsOf(IntStream.range(11,100).boxed().collect(Collectors.toList()));
    }
}

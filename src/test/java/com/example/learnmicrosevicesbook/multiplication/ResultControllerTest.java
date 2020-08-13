package com.example.learnmicrosevicesbook.multiplication;

import com.example.learnmicrosevicesbook.multiplication.controller.MultiplicationController;
import com.example.learnmicrosevicesbook.multiplication.controller.ResultController;
import com.example.learnmicrosevicesbook.multiplication.entity.Multiplication;
import com.example.learnmicrosevicesbook.multiplication.entity.MultiplicationResultAttempt;
import com.example.learnmicrosevicesbook.multiplication.entity.RequestResponce;
import com.example.learnmicrosevicesbook.multiplication.entity.User;
import com.example.learnmicrosevicesbook.multiplication.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@SpringJUnitConfig
@WebMvcTest(ResultController.class)
public class ResultControllerTest {
    @MockBean
    MultiplicationService service;

    @Autowired
    private MockMvc mvc;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<MultiplicationResultAttempt> json;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonList;
    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    private void parmetizedTest(final boolean correct) throws Exception {
        // given
        given(service.checkAttempt(any(MultiplicationResultAttempt.class)))
                .willReturn(correct);
        User user = new User("john");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                user, multiplication, 3500,correct);
        // when
        String j =json.write(attempt).getJson();
        log.info(j);
        MockHttpServletResponse response = mvc.perform(
                post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(j))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        MultiplicationResultAttempt result = json.parseObject(response.getContentAsString());
        assertThat(result.isCorrect()).isEqualTo(correct);
    }
    @Test
    public void getCorrectResult() throws Exception{
            parmetizedTest(true);
    }
    @Test
    public void getCorrectNotResult() throws Exception{
        parmetizedTest(false);
    }

    @Test
    public void getList() throws Exception {
        // given
        Multiplication mult = new Multiplication(50,60);
        User user = new User("test_user");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user,mult,300,false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user,mult,30000,false);
        MultiplicationResultAttempt attempt3 = new MultiplicationResultAttempt(user,mult,3000,false);
        List<MultiplicationResultAttempt> attempts = Arrays.asList(attempt1,attempt2,attempt3);
        given(service.getStatsForUser("test_user")).willReturn(attempts);
        // when
        String j =jsonList.write(attempts).getJson();
        log.info(j);
        MockHttpServletResponse response = mvc.perform(
                get("/results?username=test_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<MultiplicationResultAttempt> result = jsonList.parseObject(response.getContentAsString());
        assertThat(result).isEqualTo(attempts);
    }
}

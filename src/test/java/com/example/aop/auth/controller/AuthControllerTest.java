package com.example.aop.auth.controller;

import com.example.aop.auth.dto.Login;
import com.example.aop.auth.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Autowired
    public void setAuthControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @Transactional
    @DisplayName("signin :: 정상 케이스")
    void signin() throws Exception {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("ddhhmmss");
        String strNow = dateFormat.format(now);

        String content = objectMapper.writeValueAsString(
                User.builder()
                        .id("test" + strNow)
                        .password("0000")
                        .build());

        mvc.perform(post("/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("login :: 정상 케이스")
    void login() throws Exception {
        String content = objectMapper.writeValueAsString(
                Login.builder()
                        .id("hjkim")
                        .password("asdf")
                        .build());

        mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
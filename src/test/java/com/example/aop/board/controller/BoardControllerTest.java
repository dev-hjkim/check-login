package com.example.aop.board.controller;

import com.example.aop.common.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    private MockMvc mvc;
    String accessToken;
    String expiredToken;

    @Autowired
    public void setBoardControllerTest(MockMvc mvc, JwtUtil jwtUtil) {
        this.mvc = mvc;
        this.accessToken = jwtUtil.generate("5", "ACCESS");
        this.expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzZXEiOiI1IiwiaWF0IjoxNjU1Mzk1NjI5LCJleHAiOjE2NTUzOTc0Mjl9.IHyyOvibe_GD6Zhteur-K9CrZRzvOfVA4c1EyzsyAWk";
    }

    @Test
    @DisplayName("tokenTest :: 정상 케이스")
    void tokenTest() throws Exception {
        mvc.perform(get("/v1/boards")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("tokenTest :: 토큰 미존재 케이스")
    void tokenTestRequiredAccessToken() throws Exception {
        mvc.perform(get("/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("tokenTest :: 토큰 만료 케이스")
    void tokenTestExpiredAccessToken() throws Exception {
        mvc.perform(get("/v1/boards")
                        .header("Authorization", expiredToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
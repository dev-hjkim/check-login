package com.example.aop.auth.controller;

import com.example.aop.auth.dto.Login;
import com.example.aop.auth.dto.User;
import com.example.aop.auth.model.Member;
import com.example.aop.auth.service.AuthService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;


    /**
     * 회원가입
     *
     * @author hjkim
     * @param user-id, password
     * @return Member-userId, regDt, updDt
     */
    @PostMapping(value="/signin")
    public Member signin(@Valid @RequestBody User user) {
        logger.info("signin ::: {}", user);

        Member member = Member.builder()
                .userId(user.getId())
                .password(user.getPassword())
                .build();

        return authService.signin(member);
    }

    /**
     * 로그인
     *
     * @author hjkim
     * @param login-id, password
     * @return User-id, accessToken, refreshToken
     */
    @PostMapping(value="/login")
    public User login(@Valid @RequestBody Login login) {
        logger.info("login ::: {}", login);

        Member member = Member.builder()
                .userId(login.getId())
                .password(login.getPassword())
                .build();

        return authService.findUser(member);
    }
}

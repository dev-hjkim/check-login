package com.example.aop.auth.service.impl;

import com.example.aop.auth.dto.User;
import com.example.aop.auth.model.Member;
import com.example.aop.auth.repository.AuthRepository;
import com.example.aop.auth.service.AuthService;
import com.example.aop.common.exception.UserNotFoundException;
import com.example.aop.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public Member signin(Member member) {
        boolean isDuplicated = authRepository.isDuplicated(member.getUserId());

        if (isDuplicated) {
            throw new DuplicateKeyException("userId");
        }

        authRepository.signin(member);
        return member;
    }

    @Override
    public User findUser(Member member) {
        Member registered = authRepository.findUser(member);

        if (registered == null) {
            throw new UserNotFoundException();
        }

        return new User(registered.getUserId(),
                jwtUtil.generate(registered.getMemberNo(),"ACCESS"),
                jwtUtil.generate(registered.getMemberNo(), "REFRESH"));
    }
}

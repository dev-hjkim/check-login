package com.example.aop.auth.service;

import com.example.aop.auth.dto.User;
import com.example.aop.auth.model.Member;

public interface AuthService {
    Member signin(Member member);

    User findUser(Member member);
}

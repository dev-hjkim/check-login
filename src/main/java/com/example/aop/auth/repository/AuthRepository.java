package com.example.aop.auth.repository;

import com.example.aop.auth.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRepository {
    boolean isDuplicated(String userId);
    void signin(Member member);
    Member findUser(Member member);
}

package com.example.aop.common.exception;

import com.example.aop.common.dto.ResultType;

public class AccessTokenRequiredException extends BaseException {

    private static final long serialVersionUID = 2748885175496513946L;

    public AccessTokenRequiredException() {
        super(ResultType.ACCESS_TOKEN_REQUIRED, "Access token is required");
    }
}

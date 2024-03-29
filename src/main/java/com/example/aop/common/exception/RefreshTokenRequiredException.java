package com.example.aop.common.exception;

import com.example.aop.common.dto.ResultType;

public class RefreshTokenRequiredException extends BaseException {

    private static final long serialVersionUID = 5600656707384112141L;

    public RefreshTokenRequiredException() {
        super(ResultType.REFRESH_TOKEN_REQUIRED, "Refresh token is required");
    }
}

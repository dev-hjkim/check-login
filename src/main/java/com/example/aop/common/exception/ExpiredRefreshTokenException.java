package com.example.aop.common.exception;

import com.example.aop.common.dto.ResultType;

public class ExpiredRefreshTokenException extends BaseException {

    private static final long serialVersionUID = 6294109076071064296L;

    public ExpiredRefreshTokenException() {
        super(ResultType.EXPIRED_REFRESH_TOKEN, "Refresh token is expired");
    }
}

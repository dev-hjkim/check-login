package com.example.aop.common.exception;

import com.example.aop.common.dto.ResultType;
import lombok.Getter;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1733247474863885433L;

    @Getter
    private final ResultType resultType;

    public BaseException(ResultType resultType, String message) {
        super(message);
        this.resultType = resultType;
    }
}
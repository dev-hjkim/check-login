package com.example.aop.common.converter;


import com.example.aop.common.dto.Result;
import com.example.aop.common.dto.ResultType;
import com.example.aop.common.dto.ResultWithoutData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
@RequiredArgsConstructor
public class ResultConverter extends MappingJackson2HttpMessageConverter {

    private final HttpServletResponse response;

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Result result;

        if (object instanceof ResultType) {
            result = new ResultWithoutData((ResultType) object);
        } else {
            result = new Result(object);
        }
        response.setStatus(result.getStatus().value());
        super.writeInternal(result, type, outputMessage);
    }
}

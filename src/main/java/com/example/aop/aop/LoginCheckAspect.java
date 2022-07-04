package com.example.aop.aop;

import com.example.aop.common.exception.AccessTokenRequiredException;
import com.example.aop.common.exception.ExpiredAccessTokenException;
import com.example.aop.common.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class LoginCheckAspect {
    private JwtUtil jwtUtil;

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Around(value = "@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint pjp, LoginCheck loginCheck) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null || "".equals(token)) {
            throw new AccessTokenRequiredException();
        }

        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredAccessTokenException();
        }

        String userSeq = jwtUtil.getUserSeqFromToken(token);

        Method method = MethodSignature.class.cast(pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            for (Annotation parameterAnnotation : parameterAnnotations[i]) {
                if (parameterAnnotation.annotationType() == UserSeqParam.class && method.getParameterTypes()[i] == String.class) {
                    args[i] = userSeq;
                }
            }
        }
        return pjp.proceed(args);
    }
}

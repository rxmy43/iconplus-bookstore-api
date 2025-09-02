package com.ramy.onlinebookstore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.dto.response.ApiResponse;

@Aspect
@Component
public class ResponseSuccessAspect {

    @Around("@annotation(responseMessage)")
    public Object wrapWithMessage(ProceedingJoinPoint joinPoint, ResponseSuccessMessage responseMessage)
            throws Throwable {
        Object result = joinPoint.proceed();

        if (result instanceof ApiResponse<?> apiResponse) {
            apiResponse.setMessage(responseMessage.value());
            return apiResponse;
        }

        return result;
    }
}

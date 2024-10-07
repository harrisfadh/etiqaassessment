package com.example.demo.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ApiLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Before("execution(* com.example.demo.controller.*.*(..))")
    public void logRequest(JoinPoint joinPoint) {
        logger.info("Incoming request to: {} with args: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.controller.*.*(..))", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        logger.info("Outgoing response from: {} with result: {}", joinPoint.getSignature(), result);
    }
}

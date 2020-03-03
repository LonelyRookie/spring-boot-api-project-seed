package com.company.project.core;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志通知
 *
 * @ClassName LogAdvice
 * @Description 日志通知
 * @Author HuangCanCan
 * @Date 2019/8/13 17:26
 * @Version 1.0
 **/
@Aspect
@Component
@Slf4j
public class LogAdvice {

    @Pointcut("execution(* com.company.project.web..*.*(..))")
    public void webLog() {
    }

    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("客户端IP : {}", request.getRemoteAddr());
        log.info("请求地址 : {}", request.getRequestURL().toString());
        log.info("请求方式 : {}", request.getMethod());
        log.info("请求方法 : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        // 跳过 request 和 response, 切面中不要将这两个东西直接转成json，会报错
        List<Object> params = Arrays.stream(joinPoint.getArgs()).filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))).collect(Collectors.toList());
        log.info("请求参数 : {}", JSON.toJSONString(params));

    }

    /**
     * 返回通知：目标方法正常执行完毕时执行以下代码
     *
     * @param result
     * @throws Throwable
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        // 处理完请求，返回内容
        log.info("响应数据 : {}", JSON.toJSONString(result));
    }
}

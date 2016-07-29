package com.sxk.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//@Aspect
//@Component
public class WebLogAspect2 {

    private static final Logger                 logger      = LoggerFactory.getLogger(WebLogAspect2.class);
    //线程同步
    private static final ThreadLocal<Boolean>   isRecordlog = new ThreadLocal<>();

    private static final ThreadLocal<LogEntity> logLocal    = new ThreadLocal<>();

    @Pointcut("execution(public * com.sxk.web.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    @Order(1)
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        if (!method.toLowerCase().equals("get")) {
            String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            String requestBody = Arrays.toString(joinPoint.getArgs());
            LogEntity logMsg = new LogEntity(request.getRemoteAddr(), startTime, url, classMethod, requestBody);
            logLocal.set(logMsg);
            //logger.info("before: " + logMsg.toString());
            isRecordlog.set(true);
        } else {
            isRecordlog.set(false);
        }
    }

    /**
     * AfterReturning 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     * */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        if (isRecordlog.get()) {
            LogEntity logMsg = logLocal.get();
            long startTime = logMsg.getStarttime();
            int runtime = (int) (System.currentTimeMillis() - startTime);
            logMsg.setRuntime(runtime);
            if (ret instanceof String) {
                logMsg.setResponseBody(String.valueOf(ret));
            } else if (ret instanceof JSONObject) {
                logMsg.setResponseBody(JSON.toJSONString(ret));
            }
            logger.info("afterReturn: " + logMsg.toString());
        }
    }

}

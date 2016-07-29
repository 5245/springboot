package com.sxk.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

@Aspect
@Component
public class WebLogAspect {

    private Logger    logger    = LoggerFactory.getLogger(WebLogAspect.class);
    //线程
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.sxk.web.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    @Order(1)
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        Object[] objs = joinPoint.getArgs();
        logger.info("objs:" + getString(objs));
        logger.info("this:" + joinPoint.getThis());

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    /** 
     * 这个类主要是用于输出方法的参数 
     *  
     * @param objs 
     * @return 
     */
    @SuppressWarnings("unchecked")
    public String getString(Object[] objs) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, len = objs.length; i < len; i++) {
            if (objs[i] instanceof String) {
                stringBuffer.append("String类型：" + objs[i].toString());
            } else if (objs[i] instanceof Map) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) objs[i];
                HashMap<String, Object> map = hashMap;
                HashSet<String> set = (HashSet<String>) map.keySet();
                stringBuffer.append("Map类型");
                for (String str : set) {
                    stringBuffer.append(str + "=" + map.get(str));
                }
            } else if (objs[i] instanceof Integer) {
                stringBuffer.append("整数类型：");
                stringBuffer.append(objs[i].toString());
            } else {
                stringBuffer.append(objs[i].toString());
            }
        }
        return stringBuffer.toString();
    }
}

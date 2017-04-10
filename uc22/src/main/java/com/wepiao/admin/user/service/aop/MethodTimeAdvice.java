package com.wepiao.admin.user.service.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/** 
 * AOP的方式用来监控方法的执行时间
 * @author Jin Song 
 */

public class MethodTimeAdvice implements MethodInterceptor {

    /** 
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation) 
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch  
        long startTime = System.nanoTime();
        Object result = null;
        //监控的类名  
        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
        //监控的方法名  
        String methodName = className + "." + invocation.getMethod().getName();
        try {
            //这个是我们监控的bean的执行并返回结果  
            result = invocation.proceed();
        } catch (Throwable e) {
            //监控的参数  

            //            logger.error("数据库执行异常,方法名：" + methodName + "参数:" + getString(objs), e);  
            throw e;
        }
        long endTime = System.nanoTime();
        Object[] objs = invocation.getArguments();
        System.out.println("方法名：" + methodName + "参数:" + getString(objs) + "执行时间:" + (endTime - startTime) + " ns [" + methodName + "]");
        return result;
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
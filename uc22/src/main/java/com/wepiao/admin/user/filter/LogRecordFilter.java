package com.wepiao.admin.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.enumeration.LogType;
import com.wepiao.user.common.util.LogRecordUtil;
import com.wepiao.user.common.util.StringUtil;

public class LogRecordFilter implements Filter {

    private static final Logger logger      = LoggerFactory.getLogger(LogRecordFilter.class);
    private static final Logger logUserInfo = LoggerFactory.getLogger(Constants.LOG_USER_INFO);
    private static final Logger logUserTag  = LoggerFactory.getLogger(Constants.LOG_USER_TAG);
    private static final String logBody     = "HTTP Body:{}";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startMillis = System.currentTimeMillis();
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding("UTF-8");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String pathInfo = httpRequest.getPathInfo();
        LogType logType = LogRecordUtil.judgeLogType(pathInfo);
        if (LogType.QUERY == logType) {
            chain.doFilter(request, response);
        } else {
            String requestId = httpRequest.getHeader("requestId");
            String userIp = httpRequest.getHeader("User-Ip");
            // uri=uc/v1/addusertag/dynamic  pathInfo=addusertag/dynamic
            //  String uri = httpRequest.getRequestURI();
            String url = httpRequest.getRequestURL().toString();
            HttpServletRequestCopier requestCopier = new HttpServletRequestCopier(httpRequest);
            String reqBody = IOUtils.toString(requestCopier.getReader());
            // 读过之后要重置!
            requestCopier.resetInputStream();
            HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
            try {
                chain.doFilter(requestCopier, responseCopier);
            } catch (Exception e) {
                logger.error("chain.doFilter error", e);
            }
            try {
                responseCopier.flushBuffer();
                String respBody = new String(responseCopier.getCopy(), response.getCharacterEncoding());

                String contentType = response.getContentType();
                if (contentType != null && contentType.indexOf("json") != -1 && logType != LogType.QUERY) {
                    logMsg(requestId, userIp, reqBody, respBody, startMillis, url, logType);
                }
            } catch (Exception e) {
                logger.error("logRequest error", e);
            }
        }

    }

    private void logMsg(String requestId, String userIp, String reqBody, String respBody, long startMillis, String url, LogType logType) {
        JSONObject logMsg = new JSONObject();
        logMsg.put("requestId", requestId);
        logMsg.put("User-Ip", userIp);
        if (StringUtil.isEmptyCheckTrim(reqBody)) {
            logMsg.put("requestBody", new JSONObject());
        } else {
            JSONObject requestObj = JSON.parseObject(reqBody);
            logMsg.put("requestBody", requestObj);
        }
        if (StringUtil.isEmptyCheckTrim(respBody)) {
            logMsg.put("responseBody", new JSONObject());
        } else {
            JSONObject responseObj = JSON.parseObject(respBody);
            logMsg.put("responseBody", responseObj);

        }
        logMsg.put("requestUrl", url);
        logMsg.put("requestTime", String.valueOf(startMillis));
        long endMillis = System.currentTimeMillis();
        logMsg.put("runtime", endMillis - startMillis);
        if (logType == LogType.USERTAG) {
            logUserTag.info(logBody, logMsg);
        } else if (logType == LogType.USERINFO) {
            logUserInfo.info(logBody, logMsg);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

package com.sxk.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private HttpServletRequest  request;
    private HttpServletResponse response;
    private HttpSession         session;

    @ModelAttribute
    protected void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    protected HttpSession getSession() {
        return session;
    }

    protected String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    protected String getIp() {
        return request.getRemoteAddr();
    }

}

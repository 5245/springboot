package com.sxk.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sxk.web.exception.ServiceException;

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

    /**
     * 校验
     * @param bindingResult
     */
    protected void valid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errs = bindingResult.getFieldErrors();
            StringBuffer sb = new StringBuffer();
            for (FieldError err : errs) {
                sb.append(",");
                sb.append(err.getDefaultMessage());
            }
            String msg = sb.toString();
            if (StringUtils.isNotEmpty(msg)) {
                msg = msg.substring(1);
            }
            throw new ServiceException(msg);
        }
    }
}

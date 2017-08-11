package com.sxk.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseContorller {

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

    protected String getHeaderString(String name) {
        String value = "";
        if (StringUtils.isNotBlank(name)) {
            value = request.getHeader(name);
        }
        return value;
    }

    /**
     * @return  系统base根目录
     */
    protected String getContextPath() {
        return request.getContextPath();
    }

    /**
     * 参数校验
     * @param bindingResult
     */
    protected void valid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errs = bindingResult.getFieldErrors();
            StringBuilder sb = new StringBuilder();
            errs.forEach(err -> {
                sb.append(",");
                sb.append(err.getDefaultMessage());
            });
            String msg = sb.toString();
            if (StringUtils.isNotBlank(msg)) {
                msg = msg.substring(1);
            }
            throw new ServiceException(msg);
        }
    }
}

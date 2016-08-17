package com.sxk.bootstrap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sxk.web.exception.ServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        ModelAndView view = new ModelAndView();
//        view.addObject("exception", e);
//        view.addObject("url", req.getRequestURL());
//        view.setViewName("error");
//        return view;
//    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public JSONObject serviceHandler(HttpServletRequest req, ServiceException e) throws Exception {
        JSONObject result = new JSONObject();
        result.put("errcode", e.getErrcode());
        result.put("msg", e.getMsg());
        return result;
    }

}

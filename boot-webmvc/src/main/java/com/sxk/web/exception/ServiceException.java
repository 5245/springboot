package com.sxk.web.exception;

/**
 * @description 系统业务异常
 * @author sxk
 * @date 2016年5月30日
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7973973811508851043L;

    private int               errcode          = -1;
    private String            msg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super();
        this.msg = msg;
    }

    public ServiceException(int errcode, String msg) {
        super();
        this.errcode = errcode;
        this.msg = msg;
    }

    public ServiceException(int errcode, String msg, Throwable cause) {
        super(cause);
        this.errcode = errcode;
        this.msg = msg;
    }

    public ServiceException(String msg, Throwable cause) {
        super(cause);
        this.msg = msg;
    }
}

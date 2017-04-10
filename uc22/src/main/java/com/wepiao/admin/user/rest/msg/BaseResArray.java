package com.wepiao.admin.user.rest.msg;

public class BaseResArray {
    private int    ret = 0;
    private int    sub = 0;
    private String msg;
    private Object data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BaseResArray(int ret, int sub, String msg, Object data) {
        super();
        this.ret = ret;
        this.sub = sub;
        this.msg = msg;
        this.data = data;
    }
}

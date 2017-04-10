package com.wepiao.admin.user.rest.msg;

public class MobileVerifyRes extends BaseReq {
    private boolean isSafe;
    private Integer level;

    public boolean getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(boolean isSafe) {
        this.isSafe = isSafe;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public MobileVerifyRes(boolean isSafe) {
        super();
        this.isSafe = isSafe;
    }

    public MobileVerifyRes() {
        super();
    }
}

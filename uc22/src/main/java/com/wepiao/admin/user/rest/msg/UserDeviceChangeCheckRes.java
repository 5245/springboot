package com.wepiao.admin.user.rest.msg;

public class UserDeviceChangeCheckRes extends BaseRes {
    private Boolean devicechanged;

    public Boolean getDevicechanged() {
        return devicechanged;
    }

    public void setDevicechanged(Boolean devicechanged) {
        this.devicechanged = devicechanged;
    }

    public UserDeviceChangeCheckRes() {
        super();
    }

    public UserDeviceChangeCheckRes(Boolean devicechanged) {
        super();
        this.devicechanged = devicechanged;
    }
}

package com.wepiao.admin.user.rest.msg;

public class UserDeviceChangeCheckReq extends BaseReq {
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserDeviceChangeCheckReq() {
        super();
    }

    public UserDeviceChangeCheckReq(String mobileNo) {
        super();
        this.mobileNo = mobileNo;
    }
}

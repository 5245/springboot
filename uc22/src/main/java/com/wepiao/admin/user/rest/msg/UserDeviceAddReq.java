package com.wepiao.admin.user.rest.msg;

public class UserDeviceAddReq extends BaseReq {
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserDeviceAddReq() {
        super();
    }

    public UserDeviceAddReq(String mobileNo) {
        super();
        this.mobileNo = mobileNo;
    }
}

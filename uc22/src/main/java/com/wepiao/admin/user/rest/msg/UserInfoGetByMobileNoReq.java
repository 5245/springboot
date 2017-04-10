package com.wepiao.admin.user.rest.msg;

public class UserInfoGetByMobileNoReq extends BaseReq {
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserInfoGetByMobileNoReq() {
        super();
    }

    public UserInfoGetByMobileNoReq(String mobileNo) {
        super();
        this.mobileNo = mobileNo;
    }

}

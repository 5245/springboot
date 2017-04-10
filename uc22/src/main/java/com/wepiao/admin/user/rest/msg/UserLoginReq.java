package com.wepiao.admin.user.rest.msg;

public class UserLoginReq extends BaseReq {
    private String mobileNo;
    private String password;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginReq() {

    }

    public UserLoginReq(String mobileNo, String password) {
        super();
        this.mobileNo = mobileNo;
        this.password = password;
    }
}

package com.wepiao.admin.user.rest.msg;

public class OpenIdListGetByMobileReq extends BaseReq {
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public OpenIdListGetByMobileReq() {
        super();
    }

    public OpenIdListGetByMobileReq(String mobileNo) {
        super();
        this.mobileNo = mobileNo;
    }
}

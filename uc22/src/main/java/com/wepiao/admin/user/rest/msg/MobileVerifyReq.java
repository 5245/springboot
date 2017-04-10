package com.wepiao.admin.user.rest.msg;

public class MobileVerifyReq extends BaseReq {
    private String mobileNo;
    private String userIp;
    private String operation;
    private long   timeStamp;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public MobileVerifyReq() {
        super();
    }

    public MobileVerifyReq(String mobileNo, String userIp, String operation, long timeStamp) {
        super();
        this.mobileNo = mobileNo;
        this.userIp = userIp;
        this.operation = operation;
        this.timeStamp = timeStamp;
    }

}

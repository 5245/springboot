package com.wepiao.admin.user.rest.msg;

public class UserReceiverMobileGetRes extends BaseRes {

    private String  mobileno;

    private boolean isVerified;

    //  private Date updateTime;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    //  public Date getUpdateTime() {
    //    return updateTime;
    //  }
    //
    //  public void setUpdateTime(Date updateTime) {
    //    this.updateTime = updateTime;
    //  }

    public UserReceiverMobileGetRes(String mobileno, boolean isVerified) {
        super();
        this.mobileno = mobileno;
        this.isVerified = isVerified;
    }

    public UserReceiverMobileGetRes() {
        super();
    }

}

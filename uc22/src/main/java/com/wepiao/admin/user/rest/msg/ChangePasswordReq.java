package com.wepiao.admin.user.rest.msg;

public class ChangePasswordReq extends BaseReq {
    private String  memberId;
    private String  newPassword;
    private String  oldPassword;
    private Integer opType;
    private String  mobileNo;
    private String  uid;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public ChangePasswordReq() {
        super();
    }

    public ChangePasswordReq(String memberId, String newPassword, String oldPassword, Integer opType, String mobileNo) {
        super();
        this.memberId = memberId;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.opType = opType;
        this.mobileNo = mobileNo;
    }
}

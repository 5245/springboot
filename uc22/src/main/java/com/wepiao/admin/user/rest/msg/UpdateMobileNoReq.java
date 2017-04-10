package com.wepiao.admin.user.rest.msg;

public class UpdateMobileNoReq extends BaseReq {
    private String memberId;
    private String newMobileNo;
    private String oldMobileNo;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNewMobileNo() {
        return newMobileNo;
    }

    public void setNewMobileNo(String newMobileNo) {
        this.newMobileNo = newMobileNo;
    }

    public String getOldMobileNo() {
        return oldMobileNo;
    }

    public void setOldMobileNo(String oldMobileNo) {
        this.oldMobileNo = oldMobileNo;
    }

    public UpdateMobileNoReq() {
    }

    public UpdateMobileNoReq(String memberId, String newMobileNo, String oldMobileNo) {
        super();
        this.memberId = memberId;
        this.newMobileNo = newMobileNo;
        this.oldMobileNo = oldMobileNo;
    }
}

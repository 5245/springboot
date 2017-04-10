package com.wepiao.admin.user.rest.msg;

public class UserInfoGetByUIDReq extends BaseReq {
    private String memberId;
    private String uid;

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

    public UserInfoGetByUIDReq() {
        super();
    }

    public UserInfoGetByUIDReq(String memberId) {
        super();
        this.memberId = memberId;
    }
}

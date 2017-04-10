package com.wepiao.admin.user.rest.msg.dragon;

import com.wepiao.admin.user.rest.msg.BaseReq;

public class OpenIdGetByUIDReq extends BaseReq {
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

    public OpenIdGetByUIDReq() {
        super();
    }

    public OpenIdGetByUIDReq(String memberId) {
        super();
        this.memberId = memberId;
    }
}

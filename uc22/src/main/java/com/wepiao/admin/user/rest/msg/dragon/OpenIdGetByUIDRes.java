package com.wepiao.admin.user.rest.msg.dragon;

import com.wepiao.admin.user.rest.msg.BaseRes;

public class OpenIdGetByUIDRes extends BaseRes {
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public OpenIdGetByUIDRes() {
    }

    public OpenIdGetByUIDRes(String openId) {
        super();
        this.openId = openId;
    }
}

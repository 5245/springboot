package com.wepiao.admin.user.rest.msg.dragon;

import com.wepiao.admin.user.rest.msg.BaseReq;

public class OpenIdInfoGetReq extends BaseReq {
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public OpenIdInfoGetReq() {
        super();
    }

    public OpenIdInfoGetReq(String openId) {
        super();
        this.openId = openId;
    }
}

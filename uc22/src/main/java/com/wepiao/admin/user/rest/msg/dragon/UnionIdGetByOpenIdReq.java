package com.wepiao.admin.user.rest.msg.dragon;

import com.wepiao.admin.user.rest.msg.BaseReq;

public class UnionIdGetByOpenIdReq extends BaseReq {
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public UnionIdGetByOpenIdReq() {
        super();
    }

    public UnionIdGetByOpenIdReq(String openId) {
        super();
        this.openId = openId;
    }
}

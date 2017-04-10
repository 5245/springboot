package com.wepiao.admin.user.rest.msg.dragon;

import com.wepiao.admin.user.rest.msg.BaseRes;

public class UnionIdGetByOpenIdRes extends BaseRes {
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UnionIdGetByOpenIdRes() {
    }

    public UnionIdGetByOpenIdRes(String uniqueId) {
        super();
        this.uniqueId = uniqueId;
    }
}

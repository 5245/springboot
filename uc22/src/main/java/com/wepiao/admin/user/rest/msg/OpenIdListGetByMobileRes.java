package com.wepiao.admin.user.rest.msg;

import java.util.List;

import com.wepiao.user.common.entry.OpenId;

public class OpenIdListGetByMobileRes extends BaseRes {
    private List<OpenId> openIdList;

    public List<OpenId> getOpenIdList() {
        return openIdList;
    }

    public void setOpenIdList(List<OpenId> openIdList) {
        this.openIdList = openIdList;
    }

    public OpenIdListGetByMobileRes(List<OpenId> openIdList) {
        super();
        this.openIdList = openIdList;
    }
}

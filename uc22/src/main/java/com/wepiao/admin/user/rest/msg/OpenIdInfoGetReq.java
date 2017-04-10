package com.wepiao.admin.user.rest.msg;

public class OpenIdInfoGetReq extends BaseReq {
    private String OpenID;

    public String getOpenID() {
        return OpenID;
    }

    public void setOpenID(String openID) {
        OpenID = openID;
    }

    public OpenIdInfoGetReq() {
        super();
    }

    public OpenIdInfoGetReq(String openID) {
        super();
        OpenID = openID;
    }

}

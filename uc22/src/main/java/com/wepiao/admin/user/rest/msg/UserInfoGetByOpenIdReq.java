package com.wepiao.admin.user.rest.msg;

public class UserInfoGetByOpenIdReq extends BaseReq {
    private String OpenID;
    private int    OtherID;

    public String getOpenID() {
        return OpenID;
    }

    public void setOpenID(String openID) {
        OpenID = openID;
    }

    public int getOtherID() {
        return OtherID;
    }

    public void setOtherID(int otherID) {
        OtherID = otherID;
    }

    public UserInfoGetByOpenIdReq() {
        super();
    }

    public UserInfoGetByOpenIdReq(String openID, int otherID) {
        super();
        OpenID = openID;
        OtherID = otherID;
    }

}

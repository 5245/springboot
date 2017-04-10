package com.wepiao.admin.user.rest.msg;

public class CinemaFavoriteListReq extends BaseReq {
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

    public CinemaFavoriteListReq() {

    }

    public CinemaFavoriteListReq(String openID, int otherID) {
        super();
        OpenID = openID;
        OtherID = otherID;
    }
}

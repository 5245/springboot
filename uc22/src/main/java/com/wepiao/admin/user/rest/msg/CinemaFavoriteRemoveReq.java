package com.wepiao.admin.user.rest.msg;

public class CinemaFavoriteRemoveReq extends BaseReq {
    private String OpenID;
    private int    OtherID;
    private int    CinemaID;

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

    public int getCinemaID() {
        return CinemaID;
    }

    public void setCinemaID(int cinemaID) {
        CinemaID = cinemaID;
    }

    public CinemaFavoriteRemoveReq() {

    }

    public CinemaFavoriteRemoveReq(String openID, int otherID, int cinemaID) {
        super();
        OpenID = openID;
        OtherID = otherID;
        CinemaID = cinemaID;
    }
}

package com.wepiao.admin.user.rest.msg;

public class OpenIdRegisterReq extends BaseReq {
    private String OpenID;
    private int    OtherID;
    private String unionId;
    private String photo;
    private String nickName;
    private int    platForm;
    private int    appkey;

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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPlatForm() {
        return platForm;
    }

    public void setPlatForm(int platForm) {
        this.platForm = platForm;
    }

    public int getAppkey() {
        return appkey;
    }

    public void setAppkey(int appkey) {
        this.appkey = appkey;
    }

    public OpenIdRegisterReq() {
        super();
    }

    public OpenIdRegisterReq(String openID, int otherID, String unionId, String photo, String nickName, int platForm, int appkey) {
        super();
        OpenID = openID;
        OtherID = otherID;
        this.unionId = unionId;
        this.photo = photo;
        this.nickName = nickName;
        this.platForm = platForm;
        this.appkey = appkey;
    }
}

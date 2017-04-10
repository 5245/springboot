package com.wepiao.admin.user.rest.msg;

public class MobileRegisterReq extends BaseReq {
    private String mobileNo;
    private String password;
    private String nickname;
    private String photoUrl;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public MobileRegisterReq() {
        super();
    }

    public MobileRegisterReq(String mobileNo, String password, String nickname, String photoUrl) {
        super();
        this.mobileNo = mobileNo;
        this.password = password;
        this.nickname = nickname;
        this.photoUrl = photoUrl;
    }
}

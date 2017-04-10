package com.wepiao.admin.user.rest.msg.dragon;

import com.wepiao.admin.user.rest.msg.BaseRes;

public class OpenIdInfoGetRes extends BaseRes {
    private String nickName;
    private String photo;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public OpenIdInfoGetRes() {
    }

    public OpenIdInfoGetRes(String nickName, String photo) {
        super();
        this.nickName = nickName;
        this.photo = photo;
    }
}

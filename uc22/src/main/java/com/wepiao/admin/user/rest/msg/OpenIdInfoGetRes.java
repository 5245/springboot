package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.annotation.JSONField;

public class OpenIdInfoGetRes extends BaseRes {
    private String openId;
    private int    otherId;
    @JSONField(name = "stat")
    private int    status;
    private int    bindingStatus;
    private String nickName;
    private String photo;

    // 设置特殊的_以适应json属性首字母大写的需要
    public String get_OpenID() {
        return openId;
    }

    public int get_OtherID() {
        return otherId;
    }

    public int getStatus() {
        return status;
    }

    public int getBindingStatus() {
        return bindingStatus;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void set_OpenID(String openId) {
        this.openId = openId;
    }

    public void set_OtherID(int otherId) {
        this.otherId = otherId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBindingStatus(int bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public OpenIdInfoGetRes(String openId, int otherId, int status, int bindingStatus, String nickName, String photo) {
        super();
        this.openId = openId;
        this.otherId = otherId;
        this.status = status;
        this.bindingStatus = bindingStatus;
        this.nickName = nickName;
        this.photo = photo;
    }
}

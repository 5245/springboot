package com.wepiao.admin.user.rest.msg;

public class UserInfoUploadPhotoRes extends BaseRes {
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserInfoUploadPhotoRes(String photoUrl) {
        super();
        this.photoUrl = photoUrl;
    }
}

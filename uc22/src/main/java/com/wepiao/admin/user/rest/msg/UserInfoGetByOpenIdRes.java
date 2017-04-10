package com.wepiao.admin.user.rest.msg;

import java.util.Date;

public class UserInfoGetByOpenIdRes extends UserInfoGetRes {
    private String openId;
    private int    otherId;
    private String unionId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getOtherId() {
        return otherId;
    }

    public void setOtherId(int otherId) {
        this.otherId = otherId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public UserInfoGetByOpenIdRes(String memberId, Boolean hasCredential, String extUid, String unionId, String openId, int otherId, int status,
            String mobileNo, String nickName, String city, int sex, Date birthday, String email, String photoUrl, String name, String userKey) {
        super(memberId, hasCredential, extUid, status, mobileNo, nickName, city, sex, birthday, email, photoUrl, name, userKey);
        this.openId = openId;
        this.otherId = otherId;
        this.unionId = unionId;
    }
}

package com.wepiao.admin.user.rest.msg;

@Deprecated
public class UserInfoUpdateReq extends BaseReq {
    private long   memberId;
    private String city;
    private String nickName;
    private int    sex;
    private String email;
    private String photoUrl;
    private String name;
    private String userKey;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public UserInfoUpdateReq() {
        super();
    }

    public UserInfoUpdateReq(long memberId, String city, String nickName, int sex, String email, String photoUrl, String name, String userKey) {
        super();
        this.memberId = memberId;
        this.city = city;
        this.nickName = nickName;
        this.sex = sex;
        this.email = email;
        this.photoUrl = photoUrl;
        this.name = name;
        this.userKey = userKey;
    }
}

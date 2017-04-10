package com.wepiao.admin.user.rest.msg;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class UserInfoGetRes extends BaseRes {
    private String  UID;
    private String  memberId;
    private Boolean hasCredential;
    private String  extUid;
    @JSONField(name = "stat")
    private int     status;
    private String  mobileNo;
    private String  nickName;
    private String  city;
    private int     sex;
    @JSONField(format = "yyyy-MM-dd")
    private Date    birthday;
    private String  email;
    private String  photoUrl;
    private String  name;
    private String  userKey;

    public String get_UID() {
        return UID;
    }

    public void set_UID(String UID) {
        this.UID = UID;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Boolean getHasCredential() {
        return hasCredential;
    }

    public void setHasCredential(Boolean hasCredential) {
        this.hasCredential = hasCredential;
    }

    public String getExtUid() {
        return extUid;
    }

    public void setExtUid(String extUid) {
        this.extUid = extUid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public UserInfoGetRes(String memberId, Boolean hasCredential, String extUid, int status, String mobileNo, String nickName, String city, int sex,
            Date birthday, String email, String photoUrl, String name, String userKey) {
        super();
        this.memberId = memberId;
        this.UID = memberId;
        this.hasCredential = hasCredential;
        this.extUid = extUid;
        this.status = status;
        this.mobileNo = mobileNo;
        this.nickName = nickName;
        this.city = city;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.photoUrl = photoUrl;
        this.name = name;
        this.userKey = userKey;
    }
}

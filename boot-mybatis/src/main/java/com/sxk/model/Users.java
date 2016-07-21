package com.sxk.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sxk.enumeration.Gender;
import com.sxk.enumeration.Status;

public class Users extends BaseSplittedEntry {
    private Integer uid;
    private String  mobileNo;
    private String  password;
    private String  nickName;
    private String  photo;
    private Status  status;
    private String  area;
    private Gender  sex;
    @JSONField(format = "yyyy-MM-dd")
    private Date    birthday;
    private String  email;
    private String  userName;
    private String  userKey;
    private Date    registDate;
    private Date    lastModifyTime;
    private String  cinemaFavorites;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getCinemaFavorites() {
        return cinemaFavorites;
    }

    public void setCinemaFavorites(String cinemaFavorites) {
        this.cinemaFavorites = cinemaFavorites;
    }

    public Users() {
    }

    public Users(Integer uid, String mobileNo, String password, String nickName, String photo, Status status, String area, Gender sex, Date birthday,
            String email, String userName, String userKey, Date registDate, Date lastModifyTime, String cinemaFavorites) {
        super();
        this.uid = uid;
        this.mobileNo = mobileNo;
        this.password = password;
        this.nickName = nickName;
        this.photo = photo;
        this.status = status;
        this.area = area;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.userName = userName;
        this.userKey = userKey;
        this.registDate = registDate;
        this.lastModifyTime = lastModifyTime;
        this.cinemaFavorites = cinemaFavorites;
    }
}

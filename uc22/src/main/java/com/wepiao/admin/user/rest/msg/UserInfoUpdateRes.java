package com.wepiao.admin.user.rest.msg;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class UserInfoUpdateRes extends BaseRes {
    private long   UID;
    private long   memberId;
    private String extUid;
    private String mobileNo;
    private String nickName;
    private int    sex;
    private String city;
    @JSONField(format = "yyyy-MM-dd")
    private Date   birthday;
    private String email;
    private String photoUrl;
    private String name;
    private String userKey;

    private String signature;
    private int    maritalStat;
    private int    carrer;
    private int    enrollmentYear;
    private int    highestEdu;
    private String school;
    //观影人数
    private int    watchCPNum;

    public long get_UID() {
        return UID;
    }

    public void set_UID(long UID) {
        this.UID = UID;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getExtUid() {
        return extUid;
    }

    public void setExtUid(String extUid) {
        this.extUid = extUid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getMaritalStat() {
        return maritalStat;
    }

    public void setMaritalStat(int maritalStat) {
        this.maritalStat = maritalStat;
    }

    public int getCarrer() {
        return carrer;
    }

    public void setCarrer(int carrer) {
        this.carrer = carrer;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public int getHighestEdu() {
        return highestEdu;
    }

    public void setHighestEdu(int highestEdu) {
        this.highestEdu = highestEdu;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getWatchCPNum() {
        return watchCPNum;
    }

    public void setWatchCPNum(int watchCPNum) {
        this.watchCPNum = watchCPNum;
    }

    public UserInfoUpdateRes(long memberId, String extUid, String mobileNo, String nickName, int sex, String city, Date birthday, String email,
            String photoUrl, String name, String userKey, String signature, int maritalStat, int carrer, int enrollmentYear, int highestEdu,
            String school, int watchCPNum) {
        super();
        this.memberId = memberId;
        this.UID = memberId;
        this.extUid = extUid;
        this.mobileNo = mobileNo;
        this.nickName = nickName;
        this.sex = sex;
        this.city = city;
        this.birthday = birthday;
        this.email = email;
        this.photoUrl = photoUrl;
        this.name = name;
        this.userKey = userKey;
        this.signature = signature;
        this.maritalStat = maritalStat;
        this.carrer = carrer;
        this.enrollmentYear = enrollmentYear;
        this.highestEdu = highestEdu;
        this.school = school;
        this.watchCPNum = watchCPNum;
    }

}

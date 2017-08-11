package com.wepiao.user.common.entry;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.alibaba.fastjson.annotation.JSONField;
import com.wepiao.user.common.entry.enumeration.Gender;
import com.wepiao.user.common.entry.enumeration.Status;

@Data
@EqualsAndHashCode(callSuper = false)
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
    private String  extUid;

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

    public Users(Integer uid, String mobileNo, String password, String nickName, String photo, Status status, String area, Gender sex, Date birthday,
            String email, String userName, String userKey, Date registDate, Date lastModifyTime, String cinemaFavorites, String extUid) {
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
        this.extUid = extUid;
    }
}

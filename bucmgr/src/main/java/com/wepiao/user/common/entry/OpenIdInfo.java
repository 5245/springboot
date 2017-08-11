package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.entry.enumeration.SubOtherID;
import com.wepiao.user.common.entry.enumeration.UserSource;

public class OpenIdInfo extends BaseSplittedEntry {
    private Integer       id;
    private String        openId;
    private OtherID       otherId;
    private SubOtherID    subOtherId = SubOtherID.OTHER;
    private String        nickName;
    private String        photo;
    private Date          createTime;
    private String        cinemaFavorites;
    private BindingStatus bindingStatus;
    private Date          lastLoginTime;
    private Status        status;
    private String 		  passwd;
    private UserSource    userSource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public OtherID getOtherId() {
        return otherId;
    }

    public void setOtherId(OtherID otherId) {
        this.otherId = otherId;
    }

    public SubOtherID getSubOtherId() {
		return subOtherId;
	}

	public void setSubOtherId(SubOtherID subOtherId) {
		this.subOtherId = subOtherId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCinemaFavorites() {
        return cinemaFavorites;
    }

    public void setCinemaFavorites(String cinemaFavorites) {
        this.cinemaFavorites = cinemaFavorites;
    }

    public BindingStatus getBindingStatus() {
        return bindingStatus;
    }

    public void setBindingStatus(BindingStatus bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public UserSource getUserSource() {
		return userSource;
	}

	public void setUserSource(UserSource userSource) {
		this.userSource = userSource;
	}

	public OpenIdInfo() {
    }

    public OpenIdInfo(Integer id, String openId, OtherID otherId, String nickName, String photo, Date createTime, String cinemaFavorites,
            BindingStatus bindingStatus, Date lastLoginTime, Status status) {
        this(id, openId, otherId, SubOtherID.OTHER, nickName, photo, createTime, cinemaFavorites, bindingStatus, lastLoginTime, status, null, UserSource.YUPIAO);
    }
    
    public OpenIdInfo(Integer id, String openId, OtherID otherId, SubOtherID subOtherId, String nickName, String photo, Date createTime, String cinemaFavorites,
            BindingStatus bindingStatus, Date lastLoginTime, Status status, String passwd, UserSource userSource) {
        super();
        this.id = id;
        this.openId = openId;
        this.otherId = otherId;
        this.subOtherId = subOtherId;
        this.nickName = nickName;
        this.photo = photo;
        this.createTime = createTime;
        this.cinemaFavorites = cinemaFavorites;
        this.bindingStatus = bindingStatus;
        this.lastLoginTime = lastLoginTime;
        this.status = status;
        this.passwd = passwd;
        this.userSource = userSource;
    }
}

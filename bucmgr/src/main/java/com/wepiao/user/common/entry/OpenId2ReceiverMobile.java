package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.InternalServiceType;

public class OpenId2ReceiverMobile extends BaseSplittedEntry {

    /**主键id*/
    private Integer             id;

    /**openId*/
    private String              openId;

    /**手机号*/
    private String              mobileNo;

    /**业务类型*/
    private InternalServiceType serviceType;

    /**手机号是否经过验证*/
    private Boolean             isVerified;

    /**创建时间*/
    private Date                createTime;

    /**修改时间*/
    private Date                updateTime;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public InternalServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(InternalServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public OpenId2ReceiverMobile() {
    }

    public OpenId2ReceiverMobile(String mobileNo, Boolean isVerified) {
        super();
        this.mobileNo = mobileNo;
        this.isVerified = isVerified;
    }

    public OpenId2ReceiverMobile(Integer id, String openId, String mobileNo, InternalServiceType serviceType, Boolean isVerified,
            Date createTime, Date updateTime) {
        super();
        this.id = id;
        this.openId = openId;
        this.mobileNo = mobileNo;
        this.serviceType = serviceType;
        this.isVerified = isVerified;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
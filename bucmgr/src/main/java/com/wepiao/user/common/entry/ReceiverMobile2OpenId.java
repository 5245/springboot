package com.wepiao.user.common.entry;

public class ReceiverMobile2OpenId extends BaseSplittedEntry {

    /**主键id*/
    private Integer             id;

    /**手机号*/
    private String              mobileNo;
    
    /**openId*/
    private String              openId;

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

    public ReceiverMobile2OpenId() {
    }

    public ReceiverMobile2OpenId(Integer id, String mobileNo, String openId) {
        super();
        this.id = id;
        this.openId = openId;
        this.mobileNo = mobileNo;
    }
}
package com.wepiao.admin.user.rest.msg;

public class UserReceiverMobileAddReq extends BaseReq {

    /**openId*/
    private String  id;

    /**id类型*/
    private Integer idType;

    /**手机号*/
    private String  mobileNo;

    /**业务类型, 订单1,营销2,*/
    private Integer serviceType;

    /**手机号是否经过验证*/
    private Boolean isVerified = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServicetype(Integer servicetype) {
        this.serviceType = servicetype;
    }

    public Boolean getIsverified() {
        return isVerified;
    }

    public void setIsverified(Boolean isverified) {
        this.isVerified = isverified;
    }

    public UserReceiverMobileAddReq(String id, Integer idType, String mobileNo, Integer serviceType, Boolean isVerified) {
        super();
        this.id = id;
        this.idType = idType;
        this.mobileNo = mobileNo;
        this.serviceType = serviceType;
        this.isVerified = isVerified;
    }

    public UserReceiverMobileAddReq() {
        super();
    }

}
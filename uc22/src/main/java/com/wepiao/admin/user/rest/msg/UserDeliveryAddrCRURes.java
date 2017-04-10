package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.annotation.JSONField;

public class UserDeliveryAddrCRURes extends BaseRes {
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "memberId")
    private String  memberId;
    @JSONField(name = "extUid")
    private String  extUid;
    @JSONField(name = "soureCode")
    private Integer soureCode;
    @JSONField(name = "deliveryAddress")
    private String  deliveryAddress;
    @JSONField(name = "receiver")
    private String  receiver;
    @JSONField(name = "receiverMobile")
    private String  receiverMobile;
    @JSONField(name = "districtName")
    private String  districtName;
    @JSONField(name = "countryCode")
    private String  countryCode;
    @JSONField(name = "isDefault")
    private Integer isDefault;
    @JSONField(name = "createTime")
    private Integer createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getExtUid() {
        return extUid;
    }

    public void setExtUid(String extUid) {
        this.extUid = extUid;
    }

    public Integer getSoureCode() {
        return soureCode;
    }

    public void setSoureCode(Integer soureCode) {
        this.soureCode = soureCode;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public UserDeliveryAddrCRURes() {
        super();
    }

    public UserDeliveryAddrCRURes(Integer id, String memberId, String extUid, Integer soureCode, String deliveryAddress, String receiver,
            String receiverMobile, String districtName, String countryCode, Integer isDefault, Integer createTime) {
        super();
        this.id = id;
        this.memberId = memberId;
        this.extUid = extUid;
        this.soureCode = soureCode;
        this.deliveryAddress = deliveryAddress;
        this.receiver = receiver;
        this.receiverMobile = receiverMobile;
        this.districtName = districtName;
        this.countryCode = countryCode;
        this.isDefault = isDefault;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserDeliveryAddrCRURes [id=" + id + ", memberId=" + memberId + ", extUid=" + extUid + ", soureCode=" + soureCode
                + ", deliveryAddress=" + deliveryAddress + ", receiver=" + receiver + ", receiverMobile=" + receiverMobile + ", districtName="
                + districtName + ", countryCode=" + countryCode + ", isDefault=" + isDefault + ", createTime=" + createTime + "]";
    }
}

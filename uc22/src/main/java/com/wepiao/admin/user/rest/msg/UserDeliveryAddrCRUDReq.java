package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.annotation.JSONField;

public class UserDeliveryAddrCRUDReq extends BaseReq {
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "memberId")
    private String  memberId;
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

    public UserDeliveryAddrCRUDReq() {
        super();
    }

    public UserDeliveryAddrCRUDReq(Integer id, String memberId, Integer soureCode, String deliveryAddress, String receiver, String receiverMobile,
            String districtName, String countryCode, Integer isDefault, Integer createTime) {
        super();
        this.id = id;
        this.memberId = memberId;
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
        return "UserDeliveryAddrCRUDReq [id=" + id + ", memberId=" + memberId + ", soureCode=" + soureCode + ", deliveryAddress=" + deliveryAddress
                + ", receiver=" + receiver + ", receiverMobile=" + receiverMobile + ", districtName=" + districtName + ", countryCode=" + countryCode
                + ", isDefault=" + isDefault + ", createTime=" + createTime + "]";
    }

}

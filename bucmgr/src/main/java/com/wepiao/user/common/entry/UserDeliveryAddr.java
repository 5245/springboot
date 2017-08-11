package com.wepiao.user.common.entry;

import com.wepiao.user.common.entry.enumeration.SourceCode;

/**
 * ClassName: UserDeliveryAddr <br/>
 * Function: 用户收货地址实体 <br/>
 * date: 2016年2月26日 下午2:27:23 <br/>
 *
 * @author liujie
 * @version
 */
public class UserDeliveryAddr extends BaseSplittedEntry {

    private String     id;
    private String     openId;
    private String     nationalCode;   //国家地区编码
    private String     detailedAddress; //详细地址
    private String     receiver;
    private String     receiverMobile;
    private String     zipCode;        //邮政编码
    private SourceCode sourceCode;     //地址来源渠道编号 (0 演出)
    private Integer    isDefault;      //是否为默认收货地址 1 是 ,0 否
    private Integer    isDel;          //本条是否标记为删除 1 是,0 否
    private Integer    createTime;

    public UserDeliveryAddr() {
        super();
    }

    public UserDeliveryAddr(String id, String openId, String nationalCode, String detailedAddress, String receiver, String receiverMobile,
            String zipCode, SourceCode sourceCode, Integer createTime) {
        super();
        this.id = id;
        this.openId = openId;
        this.nationalCode = nationalCode;
        this.detailedAddress = detailedAddress;
        this.receiver = receiver;
        this.receiverMobile = receiverMobile;
        this.zipCode = zipCode;
        this.sourceCode = sourceCode;
        this.createTime = createTime;
    }

    public UserDeliveryAddr(String id, String openId, String nationalCode, String detailedAddress, String receiver, String receiverMobile,
            String zipCode, SourceCode sourceCode, Integer isDefault, Integer isDel, Integer createTime) {
        super();
        this.id = id;
        this.openId = openId;
        this.nationalCode = nationalCode;
        this.detailedAddress = detailedAddress;
        this.receiver = receiver;
        this.receiverMobile = receiverMobile;
        this.zipCode = zipCode;
        this.sourceCode = sourceCode;
        this.isDefault = isDefault;
        this.isDel = isDel;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public SourceCode getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(SourceCode sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

}

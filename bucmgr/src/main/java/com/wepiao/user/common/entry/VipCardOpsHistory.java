package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.VipCardOpType;

/**
 * @description   折扣卡操作历史表（vipcardopshistory，按照memberId为key分库表）
 * vip card operations history
 * @date 2016年9月30日
 */
public class VipCardOpsHistory extends BaseSplittedEntry {
    private Integer       id;
    private Integer       memberId;     //会员ID
    private String        mobileNo;     //会员手机号
    private String        cardNo;       //折扣卡卡号
    private Integer       cardType;     //折扣卡类型
    private int           subCardType;  //折扣卡子类型
    private Integer       totalUsed;    // 资格总使用次数
    private String        newCardNo;    //重新激活后的折扣卡号
    private VipCardOpType vipCardOpType; // 操作类型
    private Date          opTime;       // 操作时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public int getSubCardType() {
        return subCardType;
    }

    public void setSubCardType(int subCardType) {
        this.subCardType = subCardType;
    }

    public Integer getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(Integer totalUsed) {
        this.totalUsed = totalUsed;
    }

    public String getNewCardNo() {
        return newCardNo;
    }

    public void setNewCardNo(String newCardNo) {
        this.newCardNo = newCardNo;
    }

    public VipCardOpType getVipCardOpType() {
        return vipCardOpType;
    }

    public void setVipCardOpType(VipCardOpType vipCardOpType) {
        this.vipCardOpType = vipCardOpType;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public VipCardOpsHistory() {
        super();
    }
    
    public VipCardOpsHistory(Integer memberId, String mobileNo, String cardNo, Integer cardType, Integer subCardType, Integer totalUsed, VipCardOpType vipCardOpType,
            Date opTime) {
        super();
        this.memberId = memberId;
        this.mobileNo = mobileNo;
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.subCardType = subCardType;
        this.totalUsed = totalUsed;
        this.vipCardOpType = vipCardOpType;
        this.opTime = opTime;
    }

    public VipCardOpsHistory(Integer memberId, String cardNo, Integer cardType, Integer subCardType, Integer totalUsed, String newCardNo, VipCardOpType vipCardOpType,
            Date opTime) {
        super();
        this.memberId = memberId;
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.subCardType = subCardType;
        this.totalUsed = totalUsed;
        this.newCardNo = newCardNo;
        this.vipCardOpType = vipCardOpType;
        this.opTime = opTime;
    }

    public VipCardOpsHistory(Integer id, Integer memberId, String mobileNo, String cardNo, Integer cardType, Integer subCardType, Integer totalUsed,
            String newCardNo, VipCardOpType vipCardOpType, Date opTime) {
        super();
        this.id = id;
        this.memberId = memberId;
        this.mobileNo = mobileNo;
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.subCardType = subCardType;
        this.totalUsed = totalUsed;
        this.newCardNo = newCardNo;
        this.vipCardOpType = vipCardOpType;
        this.opTime = opTime;
    }

}

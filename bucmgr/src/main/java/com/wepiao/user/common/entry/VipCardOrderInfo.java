package com.wepiao.user.common.entry;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.wepiao.user.common.entry.enumeration.VipCardOrderStatus;

/**
 * @description 会员卡订单信息表（vipcardorderinfo，按照orderId为key分库表）
 * @author sxk
 * @date 2016年9月30日
 *
 */
public class VipCardOrderInfo extends BaseSplittedEntry {
    private Integer            id;
    private String             orderId;
    private String             openId;
    private String             cardNo;
    private Integer            lockedNum;          //锁定会员卡资格数量
    private Date               startCountDate;     //锁定资格当时传入的 资格使用起始限定时间
    private Date               endCountDate;       //锁定资格当时传入的 资格使用结束限定时间
    private Integer            frequencyLimitation; //频次限制
    private Date               lockTime;           //锁定资格当时传入的 限定时间内使用资格的次数
    private Date               releaseTime;        //释放会员卡资格的时间
    private Date               consumeTime;        //消费会员卡资格的时间
    private Date               refundTime;         //回退会员卡资格的时间
    private VipCardOrderStatus status;             //会员卡订单的状态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getLockedNum() {
        return lockedNum;
    }

    public void setLockedNum(Integer lockedNum) {
        this.lockedNum = lockedNum;
    }

    public Date getStartCountDate() {
        return startCountDate;
    }

    public void setStartCountDate(Date startCountDate) {
        this.startCountDate = startCountDate;
    }

    public Date getEndCountDate() {
        return endCountDate;
    }

    public void setEndCountDate(Date endCountDate) {
        this.endCountDate = endCountDate;
    }

    public Integer getFrequencyLimitation() {
        return frequencyLimitation;
    }

    public void setFrequencyLimitation(Integer frequencyLimitation) {
        this.frequencyLimitation = frequencyLimitation;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public VipCardOrderStatus getStatus() {
        return status;
    }

    public void setStatus(VipCardOrderStatus status) {
        this.status = status;
    }

    public VipCardOrderInfo() {
        super();
    }

    public VipCardOrderInfo(String orderId, String openId, String cardNo, Integer lockedNum) {
        super();
        this.orderId = orderId;
        this.openId = openId;
        this.cardNo = cardNo;
        this.lockedNum = lockedNum;
    }

    public VipCardOrderInfo(String orderId, String openId, String cardNo, Integer lockedNum, Date startCountDate, Date endCountDate,
            Integer frequencyLimitation, Date lockTime, Date releaseTime, Date consumeTime, Date refundTime, VipCardOrderStatus status) {
        super();
        this.orderId = orderId;
        this.openId = openId;
        this.cardNo = cardNo;
        this.lockedNum = lockedNum;
        this.startCountDate = startCountDate;
        this.endCountDate = endCountDate;
        this.frequencyLimitation = frequencyLimitation;
        this.lockTime = lockTime;
        this.releaseTime = releaseTime;
        this.consumeTime = consumeTime;
        this.refundTime = refundTime;
        this.status = status;
    }

    public VipCardOrderInfo(Integer id, String orderId, String openId, String cardNo, Integer lockedNum, Date startCountDate, Date endCountDate,
            Integer frequencyLimitation, Date lockTime, Date releaseTime, Date consumeTime, Date refundTime, VipCardOrderStatus status) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.openId = openId;
        this.cardNo = cardNo;
        this.lockedNum = lockedNum;
        this.startCountDate = startCountDate;
        this.endCountDate = endCountDate;
        this.frequencyLimitation = frequencyLimitation;
        this.lockTime = lockTime;
        this.releaseTime = releaseTime;
        this.consumeTime = consumeTime;
        this.refundTime = refundTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}

package com.wepiao.user.common.entry;

import java.util.Date;

/**
 * @description   会员卡基础信息表（vipcardinfo，按照cardNo为key分库表）
 * @date 2016年9月30日
 *
 */
public class VipCardInfo extends BaseSplittedEntry {
    private Integer id;
    private String  cardNo;
    private Integer cardType;      //卡类型      大地,火凤凰
    private int subCardType;   //卡的子类型     半年, 年
    private String  memberId;
    private Integer totalUsed;     // 资格总使用次数
    private Date    startCountDate; // 资格使用起始限定时间
    private Date    endCountDate;  // 资格使用结束限定时间
    private Date    createTime;    // 开卡时间
    private Date    updateTime;    // 会员卡信息改变时间（库存改变 不影响此值）
    private Date    expireTime;    // 折扣卡过期时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(Integer totalUsed) {
        this.totalUsed = totalUsed;
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public VipCardInfo() {
        super();
    }

    public VipCardInfo(String cardNo, Integer cardType, Integer subCardType, String memberId, Integer totalUsed) {
        super();
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.subCardType = subCardType;
        this.memberId = memberId;
        this.totalUsed = totalUsed;
    }

    public VipCardInfo(String cardNo, Integer cardType, Integer subCardType, String memberId, Integer totalUsed, Date startCountDate, Date endCountDate, Date createTime,
            Date updateTime) {
        super();
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.subCardType = subCardType;
        this.memberId = memberId;
        this.totalUsed = totalUsed;
        this.startCountDate = startCountDate;
        this.endCountDate = endCountDate;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public VipCardInfo(Integer id, String cardNo, Integer cardType, Integer subCardType, String memberId, Integer totalUsed, Date startCountDate, Date endCountDate,
            Date createTime, Date updateTime, Date expireTime) {
        super();
        this.id = id;
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.subCardType = subCardType;
        this.memberId = memberId;
        this.totalUsed = totalUsed;
        this.startCountDate = startCountDate;
        this.endCountDate = endCountDate;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.expireTime = expireTime;
    }

}

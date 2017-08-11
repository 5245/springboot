/**
 * Project Name:uc-common
 * File Name:PrivateVipInfoHistory.java
 * Package Name:com.wepiao.user.common.entry
 * Date:2016年12月1日下午4:18:22
 *
*/

package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.PrivateVipStatus;

/**
 * ClassName:PrivateVipInfoHistory <br/>
 * Function: 私有vip历史信息DB辅助类 <br/>
 * Date:     2016年12月1日 下午4:18:22 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public class PrivateVipInfoHistory extends BaseSplittedEntry{
    //主键,自增
    private Integer id;
    //会员id
    private Integer memberId;
    //操作移除私有vip的手机号
    private String mobileNo;
    //开通私有vip的openId
    private String openId;
    //私有vipId
    private Integer vipId;
    //私有vip开始时间
    private String startDate;
    //私有vip截止时间
    private String endDate;
    //私有vip的状态,1 可用,2 作废
    private PrivateVipStatus vipStatus;
    //私有vip创建时间
    private Date createTime;
    //私有vip作废时间
    private Date removeTime;
    
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
    public Integer getVipId() {
        return vipId;
    }
    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public PrivateVipStatus getVipStatus() {
        return vipStatus;
    }
    public void setVipStatus(PrivateVipStatus vipStatus) {
        this.vipStatus = vipStatus;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getRemoveTime() {
        return removeTime;
    }
    public void setRemoveTime(Date removeTime) {
        this.removeTime = removeTime;
    }
    public PrivateVipInfoHistory() {
        super();
    }
    
    public PrivateVipInfoHistory(Integer memberId, String mobileNo, String openId, Integer vipId, String startDate, String endDate, PrivateVipStatus vipStatus, Date createTime, Date removeTime) {
        super();
        this.memberId = memberId;
        this.mobileNo = mobileNo;
        this.openId = openId;
        this.vipId = vipId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vipStatus = vipStatus;
        this.createTime = createTime;
        this.removeTime = removeTime;
    }
    public PrivateVipInfoHistory(Integer id, Integer memberId, String mobileNo, String openId, Integer vipId, String startDate, String endDate, PrivateVipStatus vipStatus, Date createTime, Date removeTime) {
        super();
        this.id = id;
        this.memberId = memberId;
        this.mobileNo = mobileNo;
        this.openId = openId;
        this.vipId = vipId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vipStatus = vipStatus;
        this.createTime = createTime;
        this.removeTime = removeTime;
    }
    @Override
    public String toString() {
        return "PrivateVipInfoHistory [id=" + id + ", memberId=" + memberId + ", mobileNo=" + mobileNo + ", openId=" + openId + ", vipId=" + vipId + ", startDate=" + startDate + ", endDate=" + endDate + ", vipStatus =" + vipStatus + ", createTime="
                + createTime + ", removeTime=" + removeTime + "]";
    }
}

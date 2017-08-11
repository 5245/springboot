/**
 * Project Name:uc-common
 * File Name:VipInfo.java
 * Package Name:com.wepiao.user.common.entry
 * Date:2016年12月1日下午7:16:07
 *
*/

package com.wepiao.user.common.entry;

/**
 * ClassName:VipInfo <br/>
 * Function: 会员私有vip信息 <br/>
 * Date:     2016年12月1日 下午7:16:07 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public class VipInfo {

    //vipId
    private Integer vipId;
    //vip级别
    private String  vipLevel;
    //小图标
    private String  icon;
    //描述
    private String  description;
    //开始时间
    private String  startDate;
    //截止时间
    private String  endDate;
    public Integer getVipId() {
        return vipId;
    }
    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }
    public String getVipLevel() {
        return vipLevel;
    }
    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
    
    public VipInfo() {
        super();
    }
    
    public VipInfo(Integer vipId, String vipLevel, String icon, String description, String startDate, String endDate) {
        super();
        this.vipId = vipId;
        this.vipLevel = vipLevel;
        this.icon = icon;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        return "VipInfo [vipId=" + vipId + ", vipLevel=" + vipLevel + ", icon=" + icon + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}

/**
 * Project Name:uc-common
 * File Name:PrivateVipConfig.java
 * Package Name:com.wepiao.user.common.entry
 * Date:2016年11月30日下午2:35:20
 *
*/

package com.wepiao.user.common.entry;
/**
 * ClassName:PrivateVipConfig <br/>
 * Function: 私有vip配置信息db辅助类,注:此为单表 <br/>
 * Date:     2016年11月30日 下午2:35:20 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public class PrivateVipConfig {
    
    //主键
    private Integer id;
    //私有会员种类id
    private Integer vipId;
    //私有会员级别文字
    private String vipLevel;
    //私有会员小图标
    private String icon;
    //私有会员汉字描述
    private String description;
    //记录更新时间戳
    private Long updateTime;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    
    public Long getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public PrivateVipConfig() {
        super();
    }
    
    public PrivateVipConfig(Integer vipId, String vipLevel, String icon, String description, Long updateTime) {
        super();
        this.vipId = vipId;
        this.vipLevel = vipLevel;
        this.icon = icon;
        this.description = description;
        this.updateTime = updateTime;
    }
    
    public PrivateVipConfig(Integer id,Integer vipId, String vipLevel, String icon, String description, Long updateTime) {
        super();
        this.id = id;
        this.vipId = vipId;
        this.vipLevel = vipLevel;
        this.icon = icon;
        this.description = description;
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "PrivateVipConfig [id=" + id + ", vipId=" + vipId + ", vipLevel=" + vipLevel + ", icon=" + icon + ", description=" + description + ", updateTime=" + updateTime + "]";
    }
}

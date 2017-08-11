package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.DeviceIdType;
import com.wepiao.user.common.entry.enumeration.OtherID;

public class UserDevice extends BaseSplittedEntry {
    private Integer      rid;
    private String       id;
    private OtherID      idType;
    private String       deviceId;
    private DeviceIdType deviceIdType;
    private Date         createTime;
    private Date         updateTime;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OtherID getIdType() {
        return idType;
    }

    public void setIdType(OtherID idType) {
        this.idType = idType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceIdType getDeviceIdType() {
        return deviceIdType;
    }

    public void setDeviceIdType(DeviceIdType deviceIdType) {
        this.deviceIdType = deviceIdType;
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

    public UserDevice(Integer rid, String id, OtherID idType, String deviceId, DeviceIdType deviceIdType, Date createTime, Date updateTime) {
        super();
        this.rid = rid;
        this.id = id;
        this.idType = idType;
        this.deviceId = deviceId;
        this.deviceIdType = deviceIdType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

}

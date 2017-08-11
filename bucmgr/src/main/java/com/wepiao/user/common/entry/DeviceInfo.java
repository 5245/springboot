package com.wepiao.user.common.entry;

import com.wepiao.user.common.entry.enumeration.DeviceIdType;

public class DeviceInfo {
    private String       deviceId;
    private DeviceIdType deviceIdType;

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

    public DeviceInfo(String deviceId, DeviceIdType deviceIdType) {
        super();
        this.deviceId = deviceId;
        this.deviceIdType = deviceIdType;
    }

}

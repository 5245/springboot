package com.wepiao.user.common.entry;

public class UserTagValElement {
    private String tagVal;
    private long   updateTime;

    public String getTagVal() {
        return tagVal;
    }

    public void setTagVal(String tagVal) {
        this.tagVal = tagVal;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public UserTagValElement() {
    }

    public UserTagValElement(String tagVal, long updateTime) {
        super();
        this.tagVal = tagVal;
        this.updateTime = updateTime;
    }
}
